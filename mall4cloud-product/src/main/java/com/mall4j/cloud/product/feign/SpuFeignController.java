package com.mall4j.cloud.product.feign;

import com.mall4j.cloud.api.product.feign.SpuFeignClient;
import com.mall4j.cloud.api.product.vo.SkuVO;
import com.mall4j.cloud.api.product.vo.SpuAndSkuVO;
import com.mall4j.cloud.api.product.vo.SpuVO;
import com.mall4j.cloud.common.constant.StatusEnum;
import com.mall4j.cloud.common.response.ResponseEnum;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.product.service.SkuService;
import com.mall4j.cloud.product.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import java.util.Objects;

/**
 * @author FrozenWatermelon
 * @date 2020/12/8
 */
@RestController
public class SpuFeignController implements SpuFeignClient {

    @Autowired
    private SpuService spuService;

    @Autowired
    private SkuService skuService;

    @Override
    public ServerResponseEntity<SpuVO> getById(Long spuId) {
        SpuVO spuVO = spuService.getBySpuId(spuId);
        SpuVO spu = new SpuVO();
        spu.setSpuId(spuVO.getSpuId());
        spu.setName(spuVO.getName());
        spu.setMainImgUrl(spuVO.getMainImgUrl());
        return ServerResponseEntity.success(spu);
    }

    @Override
    public ServerResponseEntity<SpuAndSkuVO> getSpuAndSkuById(Long spuId, Long skuId) {
        SpuVO spu = spuService.getBySpuId(spuId);
        SkuVO sku = skuService.getSkuBySkuId(skuId);

        // 当商品状态不正常时，不能添加到购物车
        boolean spuIsNotExist = Objects.isNull(spu) || Objects.isNull(sku) || !Objects.equals(spu.getStatus(), StatusEnum.ENABLE.value()) || !Objects.equals(sku.getStatus(), StatusEnum.ENABLE.value()) || !Objects.equals(sku.getSpuId(), spu.getSpuId());
        if (spuIsNotExist) {
            // 当返回商品不存在时，前端应该将商品从购物车界面移除
            return ServerResponseEntity.fail(ResponseEnum.SPU_NOT_EXIST);
        }

        SpuAndSkuVO spuAndSku = new SpuAndSkuVO();
        spuAndSku.setSku(sku);
        spuAndSku.setSpu(spu);
        return ServerResponseEntity.success(spuAndSku);
    }

    @Override
    public ServerResponseEntity<List<SpuVO>> getSpusBySpuIds(List<Long> spuIds, String spuName, Integer isFailure) {
        return ServerResponseEntity.success(spuService.listBySpuIds(spuIds,spuName,isFailure));
    }
}
