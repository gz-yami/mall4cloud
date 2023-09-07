package com.mall4j.cloud.search.controller.multishop;

import com.mall4j.cloud.api.dto.EsPageDTO;
import com.mall4j.cloud.api.dto.ProductSearchDTO;
import com.mall4j.cloud.api.vo.EsPageVO;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.common.security.AuthUserContext;
import com.mall4j.cloud.search.constant.SearchTypeEnum;
import com.mall4j.cloud.search.manager.ProductSearchManager;
import com.mall4j.cloud.search.vo.SpuAdminVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @Author lth
 * @Date 2021/6/21 10:36
 */
@RestController("multishopSearchSpuController")
@RequestMapping("/m/search")
@Tag(name = "multishop-spu管理列表接口")
public class ProductSearchController {

    @Autowired
    private ProductSearchManager productSearchManager;

    @GetMapping("/page")
    @Operation(summary = "商品信息列表" , description = "商品信息列表")
    public ServerResponseEntity<EsPageVO<SpuAdminVO>> page(@Valid EsPageDTO pageDTO, ProductSearchDTO productSearchDTO) {
        Long shopId = AuthUserContext.get().getTenantId();
        productSearchDTO.setSearchType(SearchTypeEnum.MULTISHOP.value());
        productSearchDTO.setShopId(shopId);
        EsPageVO<SpuAdminVO> searchPage =  productSearchManager.adminPage(pageDTO, productSearchDTO);
        return ServerResponseEntity.success(searchPage);
    }

}
