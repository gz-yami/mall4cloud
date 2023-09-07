package com.mall4j.cloud.product.controller.app;

import com.mall4j.cloud.product.vo.app.*;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.product.model.SpuExtension;
import com.mall4j.cloud.product.service.SkuService;
import com.mall4j.cloud.product.service.SpuService;
import com.mall4j.cloud.api.product.vo.SpuVO;
import com.mall4j.cloud.product.vo.app.SkuAppVO;
import com.mall4j.cloud.product.vo.app.SpuAppVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import com.mall4j.cloud.common.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * spu信息
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
@RestController("appSpuController")
@RequestMapping("/ua/spu")
@Tag(name = "app-spu信息")
public class SpuController {

    @Autowired
    private SpuService spuService;

    @Autowired
    private SkuService skuService;



    @GetMapping("/prod_info")
    @Operation(summary = "商品详情信息" , description = "根据商品ID（prodId）获取商品信息")
    @Parameter(name = "spuId", description = "商品ID" , required = true)
    public ServerResponseEntity<SpuAppVO> prodInfo(@RequestParam("spuId") Long spuId) {

        SpuVO spu = spuService.getBySpuId(spuId);
        SpuAppVO spuAppVO = BeanUtil.map(spu, SpuAppVO.class);
        SpuExtension spuExtension = spuService.getSpuExtension(spuId);
        spuAppVO.setTotalStock(spuExtension.getActualStock());
        spuAppVO.setSaleNum(spuExtension.getSaleNum());
        List<SkuAppVO> skuAppVO = skuService.getSkuBySpuId(spu.getSpuId());
        spuAppVO.setSkus(skuAppVO);
        return ServerResponseEntity.success(spuAppVO);
    }
}
