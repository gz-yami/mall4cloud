package com.mall4j.cloud.product.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.mall4j.cloud.api.product.vo.SpuSkuAttrValueVO;
import com.mall4j.cloud.common.cache.constant.CacheNames;
import com.mall4j.cloud.common.cache.util.RedisUtil;
import com.mall4j.cloud.common.constant.StatusEnum;
import com.mall4j.cloud.product.dto.SkuDTO;
import com.mall4j.cloud.product.dto.SpuDTO;
import com.mall4j.cloud.product.mapper.SkuMapper;
import com.mall4j.cloud.product.model.Sku;
import com.mall4j.cloud.product.model.SkuStock;
import com.mall4j.cloud.product.model.SpuSkuAttrValue;
import com.mall4j.cloud.product.service.SkuService;
import com.mall4j.cloud.product.service.SkuStockService;
import com.mall4j.cloud.product.service.SpuSkuAttrValueService;
import com.mall4j.cloud.api.product.vo.SkuVO;
import com.mall4j.cloud.product.vo.app.SkuAppVO;
import com.mall4j.cloud.common.util.BeanUtil;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * sku信息
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private SpuSkuAttrValueService spuSkuAttrValueService;
    @Autowired
    private SkuStockService skuStockService;


    @Override
    public void save(Long spuId, List<SkuDTO> skuList) {
        skuList.forEach(sku -> {
            sku.setSpuId(spuId);
            sku.setStatus(StatusEnum.ENABLE.value());
        });
        // 处理数据，保存库存、属性
        skuMapper.saveBatch(skuList);
        List<SkuStock> skuStocks = new ArrayList<>();
        List<SpuSkuAttrValue> spuSkuAttrValues = new ArrayList<>();
        for (SkuDTO skuDTO : skuList) {
            SkuStock skuStock = new SkuStock();
            skuStock.setSkuId(skuDTO.getSkuId());
            skuStock.setStock(skuDTO.getStock());
            skuStock.setActualStock(skuDTO.getStock());
            skuStock.setLockStock(0);
            skuStocks.add(skuStock);
            List<SpuSkuAttrValue> spuSkuAttrValueList = BeanUtil.mapAsList(skuDTO.getSpuSkuAttrValues(), SpuSkuAttrValue.class);
            for (SpuSkuAttrValue spuSkuAttrValue : spuSkuAttrValueList) {
                spuSkuAttrValue.setSpuId(spuId);
                spuSkuAttrValue.setSkuId(skuDTO.getSkuId());
                spuSkuAttrValue.setStatus(StatusEnum.ENABLE.value());
                spuSkuAttrValues.add(spuSkuAttrValue);
            }
        }
        skuStockService.saveBatch(skuStocks);
        spuSkuAttrValueService.saveBatch(spuSkuAttrValues);
    }


    @Override
    public void update(Long spuId, List<SkuDTO> skuList) {
        // 获取当前商品所有的sku
        List<SkuVO> skuListDb = skuMapper.listBySpuId(spuId);
        List<Long> skuIdsDb = skuListDb.stream().map(SkuVO::getSkuId).collect(Collectors.toList());
        List<Long> skuIds = new ArrayList<>();
        List<SkuDTO> updateSkus = new ArrayList<>();
        List<SkuDTO> insertSkus = new ArrayList<>();
        for (SkuDTO sku : skuList) {
            sku.setSpuId(spuId);
            if (skuIdsDb.contains(sku.getSkuId())) {
                updateSkus.add(sku);
                skuIds.add(sku.getSkuId());
            } else if (Objects.isNull(sku.getSkuId())){
                insertSkus.add(sku);
            }
        }
        // 新增的sku--保存
        if(CollUtil.isNotEmpty(insertSkus)) {
            save(spuId,insertSkus);
        }
        // 已有的sku--更新
        if(CollUtil.isNotEmpty(updateSkus)){
            List<Sku> skus = BeanUtil.mapAsList(updateSkus, Sku.class);
            skuMapper.updateBatch(skus);
            skuStockService.updateBatch(updateSkus);
        }
        // 不存在的sku--删除
        skuIdsDb.removeAll(skuIds);
        if (CollUtil.isNotEmpty(skuIdsDb)) {
            deleteSkuBatch(skuIdsDb);
        }
    }

    @Override
    public void deleteById(Long skuId) {
        skuMapper.deleteById(skuId);
    }

    @Override
    @Cacheable(cacheNames = CacheNames.SKU_LIST_KEY, key = "#spuId",sync = true)
    public List<SkuVO> listBySpuId(Long spuId) {
        return skuMapper.listBySpuId(spuId);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = CacheNames.SKU_LIST_KEY, key = "#spuId"),
            @CacheEvict(cacheNames = CacheNames.SKU_OF_SPU_DETAIL_KEY, key = "#spuId")
    })
    public void removeSkuCacheBySpuIdOrSkuIds(Long spuId, List<Long> skuIds) {
        // 根据spuId删除缓存
        if (CollUtil.isEmpty(skuIds)) {
            // 获取当前类的代理，这样就可以利用spring的方法获取缓存了，不要删了
            SkuServiceImpl skuService = (SkuServiceImpl) AopContext.currentProxy();
            List<SkuVO> skuList =  skuService.listBySpuId(spuId);
            skuIds = skuList.stream().map(SkuVO::getSkuId).collect(Collectors.toList());
        }
        RedisUtil.deleteBatch(CacheNames.SKU_BY_ID_KEY, skuIds);
    }

    @Override
    public void deleteBySpuId(Long spuId) {
        skuMapper.updateBySpuId(spuId);
    }

    @Override
    public List<SkuVO> listBySpuIdAndExtendInfo(Long spuId) {
        return skuMapper.listBySpuIdAndExtendInfo(spuId);
    }

    @Override
    @Cacheable(cacheNames = CacheNames.SKU_BY_ID_KEY, key = "#skuId")
    public SkuVO getSkuBySkuId(Long skuId) {
        return skuMapper.getSkuBySkuId(skuId);
    }

    @Override
    public void updateAmountOrStock(SpuDTO spuDTO) {
        List<SkuDTO> skuList = spuDTO.getSkuList();
        List<Sku> skus = new ArrayList<>();
        Boolean isUpdateStock = false;
        for (SkuDTO skuDTO : skuList) {
            if (Objects.nonNull(skuDTO.getChangeStock())  && skuDTO.getChangeStock() > 0) {
                isUpdateStock = true;
                break;
            } else if (Objects.nonNull(skuDTO.getPriceFee())) {
                Sku sku = new Sku();
                sku.setSkuId(skuDTO.getSkuId());
                sku.setPriceFee(skuDTO.getPriceFee());
                skus.add(sku);
            }
        }
        if (isUpdateStock) {
            skuStockService.updateBatch(skuList);
        }
        if (CollUtil.isNotEmpty(skus)) {
            skuMapper.updateBatch(skus);
        }
    }

    @Override
    @Cacheable(cacheNames = CacheNames.SKU_OF_SPU_DETAIL_KEY, key = "#spuId",sync = true)
    public List<SkuAppVO> getSkuBySpuId(Long spuId) {
        String attrUnionAttrValue = ":";
        String attrUnionAttr = ";";
        List<SkuAppVO> skuAppList = new ArrayList<>();
        List<SkuVO> skuData = skuMapper.getSkuBySpuId(spuId);
        for (SkuVO sku : skuData) {
            SkuAppVO skuAppVO = BeanUtil.map(sku, SkuAppVO.class);
            String properties = "";
            for (SpuSkuAttrValueVO spuSkuAttrValue : sku.getSpuSkuAttrValues()) {
                properties = properties + spuSkuAttrValue.getAttrName() + attrUnionAttrValue + spuSkuAttrValue.getAttrValueName() + attrUnionAttr;
            }
            skuAppVO.setProperties(properties.substring(0, properties.length()-1));
            skuAppList.add(skuAppVO);
        }
        return skuAppList;
    }

    private void deleteSkuBatch(List<Long> skuIds) {
        List<Sku> skus = new ArrayList<>();
        for (Long skuId : skuIds) {
            Sku sku = new Sku();
            sku.setSkuId(skuId);
            sku.setStatus(StatusEnum.DELETE.value());
            skus.add(sku);
        }
        skuMapper.updateBatch(skus);
        spuSkuAttrValueService.changeStatusBySkuId(skuIds, StatusEnum.DELETE.value());
    }
}
