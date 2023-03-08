package com.mall4j.cloud.product.controller.app;

import com.mall4j.cloud.product.service.CategoryService;
import com.mall4j.cloud.api.product.vo.CategoryVO;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类信息
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
@RestController("appCategoryController")
@RequestMapping("/ua/category")
@Tag(name = "app-分类信息")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category_list")
    @Operation(summary = "获取指定分类下的分类列表（顶级分类的parentId为0,默认为一级分类）" , description = "获取指定分类下的分类列表")
    @Parameters({
            @Parameter(name = "parentId", description = "分类ID" ),
            @Parameter(name = "shopId", description = "店铺id" )
    })
    public ServerResponseEntity<List<CategoryVO>> categoryList(@RequestParam(value = "parentId", defaultValue = "0") Long parentId, @RequestParam(value = "shopId", defaultValue = "0") Long shopId) {
        List<CategoryVO> categories = categoryService.categoryList(shopId,parentId);
        return ServerResponseEntity.success(categories);
    }

    @GetMapping("/shop_category_list")
    @Operation(summary = "店铺/平台的全部分类列表接口" , description = "店铺/平台分类列表接口")
    @Parameter(name = "shopId", description = "店铺id", required = false)
    public ServerResponseEntity<List<CategoryVO>> shopCategoryList(@RequestParam(value = "shopId", defaultValue = "0") Long shopId) {
        List<CategoryVO> categories = categoryService.shopCategoryList(shopId);
        return ServerResponseEntity.success(categories);
    }
}
