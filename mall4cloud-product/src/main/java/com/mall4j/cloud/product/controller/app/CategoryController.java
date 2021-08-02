package com.mall4j.cloud.product.controller.app;

import com.mall4j.cloud.product.service.CategoryService;
import com.mall4j.cloud.api.product.vo.CategoryVO;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "app-分类信息")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category_list")
    @ApiOperation(value = "获取指定分类下的分类列表（顶级分类的parentId为0,默认为一级分类）", notes = "获取指定分类下的分类列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentId", value = "分类ID", dataType = "Long"),
            @ApiImplicitParam(name = "shopId", value = "店铺id", dataType = "Long")
    })
    public ServerResponseEntity<List<CategoryVO>> categoryList(@RequestParam(value = "parentId", defaultValue = "0") Long parentId, @RequestParam(value = "shopId", defaultValue = "0") Long shopId) {
        List<CategoryVO> categories = categoryService.categoryList(shopId,parentId);
        return ServerResponseEntity.success(categories);
    }

    @GetMapping("/shop_category_list")
    @ApiOperation(value = "店铺/平台的全部分类列表接口", notes = "店铺/平台分类列表接口")
    @ApiImplicitParam(name = "shopId", value = "店铺id", required = false, dataType = "Long")
    public ServerResponseEntity<List<CategoryVO>> shopCategoryList(@RequestParam(value = "shopId", defaultValue = "0") Long shopId) {
        List<CategoryVO> categories = categoryService.shopCategoryList(shopId);
        return ServerResponseEntity.success(categories);
    }
}
