package com.mall4j.cloud.api.product.feign;

import com.mall4j.cloud.api.product.vo.SpuAndSkuVO;
import com.mall4j.cloud.api.product.vo.SpuVO;
import com.mall4j.cloud.common.feign.FeignInsideAuthConfig;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author FrozenWatermelon
 * @date 2020/11/12
 */
@FeignClient(value = "mall4cloud-product",contextId = "spu")
public interface SpuFeignClient {

    /**
     * 通过spuId需要搜索的商品
     * @param spuId spuid
     * @return 商品信息(spuId,name,mainImgUrl)
     */
    @GetMapping(value = FeignInsideAuthConfig.FEIGN_INSIDE_URL_PREFIX + "/spu/getById")
    ServerResponseEntity<SpuVO> getById(@RequestParam("spuId") Long spuId);

    /**
     * 通过spuId需要搜索的商品
     * @param spuId spuId
     * @param skuId skuId
     * @return 商品信息
     */
    @GetMapping(value = FeignInsideAuthConfig.FEIGN_INSIDE_URL_PREFIX + "/spu/getSpuAndSkuById")
    ServerResponseEntity<SpuAndSkuVO> getSpuAndSkuById(@RequestParam("spuId") Long spuId,@RequestParam("skuId") Long skuId);


    /**
     * 根据spuId获取spu列表（所需字段：spuId、shopId、name、mainImgUrl）
     * 根据店铺id获取spu列表
     * @param spuIds 商品ids
     * @param spuName 商品名称
     * @param isFailure 是否失效
     * @return 商品列表信息
     */
    @GetMapping(value = FeignInsideAuthConfig.FEIGN_INSIDE_URL_PREFIX + "/spu/getSpusBySpuIds")
    ServerResponseEntity<List<SpuVO>> getSpusBySpuIds(@RequestParam("spuIds") List<Long> spuIds,@RequestParam(value = "spuName",defaultValue = "") String spuName,
                                                      @RequestParam(value = "isFailure",defaultValue = "1") Integer isFailure);

}
