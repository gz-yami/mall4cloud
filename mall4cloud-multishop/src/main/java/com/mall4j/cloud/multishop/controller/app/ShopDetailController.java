package com.mall4j.cloud.multishop.controller.app;

import com.mall4j.cloud.api.multishop.vo.ShopDetailVO;
import com.mall4j.cloud.common.exception.Mall4cloudException;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.multishop.service.ShopDetailService;
import com.mall4j.cloud.multishop.vo.ShopHeadInfoVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @Author lth
 * @Date 2021/6/29 18:39
 */
@RequestMapping(value = "/ua/shop_detail")
@RestController("appShopDetailController")
@Tag(name = "app-店铺详情信息")
public class ShopDetailController {

    @Autowired
    private ShopDetailService shopDetailService;

    @GetMapping("/check_shop_name")
    @Operation(summary = "验证店铺名称是否重名" , description = "验证店铺名称是否重名")
    public ServerResponseEntity<Boolean> checkShopName(@RequestParam("shopName") String shopName) {
        Boolean res = shopDetailService.checkShopName(shopName);
        return ServerResponseEntity.success(res);
    }

    @GetMapping("/head_info")
    @Operation(summary = "店铺头部信息" , description = "店铺头部信息")
    public ServerResponseEntity<ShopHeadInfoVO> getShopHeadInfo(Long shopId) {
        ShopHeadInfoVO shopHeadInfoVO = new ShopHeadInfoVO();
        ShopDetailVO shopDetailVO = shopDetailService.getByShopId(shopId);
        if (Objects.isNull(shopDetailVO)) {
            throw new Mall4cloudException("店铺不存在");
        }
        shopHeadInfoVO.setShopStatus(shopDetailVO.getShopStatus());
        if (!Objects.equals(shopDetailVO.getShopStatus(), 1)) {
            return ServerResponseEntity.success(shopHeadInfoVO);
        }
        shopHeadInfoVO.setShopId(shopId);
        shopHeadInfoVO.setType(shopDetailVO.getType());
        shopHeadInfoVO.setIntro(shopDetailVO.getIntro());
        shopHeadInfoVO.setShopLogo(shopDetailVO.getShopLogo());
        shopHeadInfoVO.setShopName(shopDetailVO.getShopName());
        shopHeadInfoVO.setMobileBackgroundPic(shopDetailVO.getMobileBackgroundPic());
        return ServerResponseEntity.success(shopHeadInfoVO);
    }
}
