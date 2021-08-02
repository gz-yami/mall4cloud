package com.mall4j.cloud.product.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.mall4j.cloud.product.dto.SkuDTO;
import com.mall4j.cloud.product.mapper.SkuStockMapper;
import com.mall4j.cloud.product.model.SkuStock;
import com.mall4j.cloud.product.service.SkuStockService;
import com.mall4j.cloud.product.vo.SkuStockVO;
import com.mall4j.cloud.api.product.vo.SkuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 库存信息
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
@Service
public class SkuStockServiceImpl implements SkuStockService {

    @Autowired
    private SkuStockMapper skuStockMapper;

    @Override
    public void save(SkuStock skuStock) {
        skuStockMapper.save(skuStock);
    }

    @Override
    public void update(SkuStock skuStock) {
        skuStockMapper.update(skuStock);
    }

    @Override
    public void deleteById(Long stockId) {
        skuStockMapper.deleteById(stockId);
    }

    @Override
    public void saveBatch(List<SkuStock> skuStocks) {
        skuStockMapper.saveBatch(skuStocks);
    }

    @Override
    public void deleteBySkuIds(List<Long> skuIds) {
        skuStockMapper.deleteBySkuIds(skuIds);
    }

    @Override
    public List<SkuStockVO> listBySkuList(List<SkuVO> skuVOList) {
        return skuStockMapper.listBySkuList(skuVOList);
    }

    @Override
    public void updateBatch(List<SkuDTO> skuList) {
        if (CollUtil.isEmpty(skuList)) {
            return;
        }
        // 如果是修改库存，此时不需要改变锁定库存
        List<SkuStock> skuStocks = new ArrayList<>();
        for (SkuDTO sku : skuList) {
            SkuStock skuStock = new SkuStock();
            if (Objects.nonNull(sku.getChangeStock()) && sku.getChangeStock() > 0) {
                skuStock.setStock(sku.getChangeStock());
                skuStock.setSkuId(sku.getSkuId());
                skuStocks.add(skuStock);
            }
        }
        if (CollUtil.isNotEmpty(skuStocks)) {
            skuStockMapper.updateStock(skuStocks);
        }
    }
}
