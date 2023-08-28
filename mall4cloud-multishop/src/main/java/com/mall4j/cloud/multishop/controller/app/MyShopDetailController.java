package com.mall4j.cloud.multishop.controller.app;

import com.mall4j.cloud.api.multishop.vo.ShopDetailVO;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.common.security.AuthUserContext;
import com.mall4j.cloud.multishop.dto.ShopDetailDTO;
import com.mall4j.cloud.multishop.service.ShopDetailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Objects;

/**
 * @Author lth
 * @Date 2021/6/30 9:37
 */
@RequestMapping(value = "/my_shop_detail")
@RestController("appMyShopDetailController")
@Tag(name = "app-我的店铺详情信息")
public class MyShopDetailController {

    @Autowired
    private ShopDetailService shopDetailService;

    @PostMapping("/create")
    @Operation(summary = "创建店铺" , description = "创建店铺")
    public ServerResponseEntity<Void> create(@Valid @RequestBody ShopDetailDTO shopDetailDTO) {
        shopDetailService.createShop(shopDetailDTO);
        return ServerResponseEntity.success();
    }

    @GetMapping
    @Operation(summary = "获取我的店铺" , description = "获取我的店铺")
    public ServerResponseEntity<ShopDetailVO> get() {
        Long shopId = AuthUserContext.get().getTenantId();
        if (Objects.isNull(shopId)) {
            return ServerResponseEntity.success(null);
        }
        return ServerResponseEntity.success(shopDetailService.getByShopId(shopId));
    }
}
