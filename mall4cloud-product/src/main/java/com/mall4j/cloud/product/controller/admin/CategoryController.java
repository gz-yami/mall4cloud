package com.mall4j.cloud.product.controller.admin;

import com.mall4j.cloud.api.product.constant.CategoryLevel;
import com.mall4j.cloud.api.product.vo.CategoryVO;
import com.mall4j.cloud.common.constant.Constant;
import com.mall4j.cloud.common.exception.Mall4cloudException;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.common.security.AuthUserContext;
import com.mall4j.cloud.product.dto.CategoryDTO;
import com.mall4j.cloud.product.model.Category;
import com.mall4j.cloud.product.service.CategoryAndSpuService;
import com.mall4j.cloud.product.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.Operation;
import com.mall4j.cloud.common.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * 分类信息
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
@RestController("platformCategoryController")
@RequestMapping("/admin/category")
@Tag(name = "admin-分类信息")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryAndSpuService categoryAndSpuService;



    @GetMapping
    @Operation(summary = "获取分类信息" , description = "根据categoryId获取分类信息")
    public ServerResponseEntity<CategoryVO> getByCategoryId(@RequestParam Long categoryId) {
        return ServerResponseEntity.success(categoryService.getById(categoryId));
    }

    @PostMapping
    @Operation(summary = "保存分类信息" , description = "保存分类信息")
    public ServerResponseEntity<Void> save(@Valid @RequestBody CategoryDTO categoryDTO) {
        if (!Objects.equals(Constant.PLATFORM_SHOP_ID, AuthUserContext.get().getTenantId()) && categoryDTO.getLevel() > CategoryLevel.SECOND.value()) {
            throw new Mall4cloudException("分类等级最高只能为二级分类");
        }
        Category category = BeanUtil.map(categoryDTO, Category.class);
        categoryService.save(category);
        categoryService.removeCategoryCache(AuthUserContext.get().getTenantId(), category.getParentId());
        return ServerResponseEntity.success();
    }

    @PutMapping
    @Operation(summary = "更新分类信息" , description = "更新分类信息")
    public ServerResponseEntity<Void> update(@Valid @RequestBody CategoryDTO categoryDTO) {
        Category category = BeanUtil.map(categoryDTO, Category.class);
        categoryService.update(category);
        categoryService.removeCategoryCache(AuthUserContext.get().getTenantId(), category.getParentId());
        return ServerResponseEntity.success();
    }

    @DeleteMapping
    @Operation(summary = "删除分类信息" , description = "根据分类信息id删除分类信息")
    public ServerResponseEntity<Void> delete(@RequestParam Long categoryId) {
        CategoryVO categoryVO = categoryService.getById(categoryId);
        categoryService.deleteById(categoryId);
        categoryService.removeCategoryCache(AuthUserContext.get().getTenantId(), categoryVO.getParentId());
        return ServerResponseEntity.success();
    }

    @GetMapping("/platform_categories")
    @Operation(summary = "获取平台所有的分类信息" , description = "获取所有的分类列表信息")
    public ServerResponseEntity<List<CategoryVO>> platformCategories() {
        return ServerResponseEntity.success(categoryService.list(Constant.PLATFORM_SHOP_ID));
    }

    @GetMapping("/shop_categories")
    @Operation(summary = "获取店铺所有的分类信息" , description = "获取店铺所有的分类信息")
    public ServerResponseEntity<List<CategoryVO>> shopCategories() {
        return ServerResponseEntity.success(categoryService.list(AuthUserContext.get().getTenantId()));
    }

    @GetMapping("/get_list_by_parent_id")
    @Operation(summary = "根据上级id，获取分类列表信息" , description = "根据上级id，获取分类列表信息")
    @Parameters(value = {
            @Parameter(name = "parentId", description = "父类id" )
    })
    public ServerResponseEntity<List<CategoryVO>> getListByParentId(@RequestParam(value = "parentId") Long parentId) {
        return ServerResponseEntity.success(categoryService.listByShopIdAndParenId(parentId, AuthUserContext.get().getTenantId()));
    }

    @PutMapping(value = "/category_enable_or_disable")
    @Operation(summary = "分类的启用或禁用" , description = "分类的启用或禁用")
    public ServerResponseEntity<Boolean> categoryEnableOrDisable(@RequestBody CategoryDTO categoryDTO) {
        Boolean isSuccess = categoryAndSpuService.categoryEnableOrDisable(categoryDTO);
        return ServerResponseEntity.success(isSuccess);
    }
}
