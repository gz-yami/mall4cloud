package com.mall4j.cloud.product.controller.platform;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.mall4j.cloud.api.product.vo.BrandVO;
import com.mall4j.cloud.common.database.dto.PageDTO;
import com.mall4j.cloud.common.database.vo.PageVO;
import com.mall4j.cloud.common.exception.Mall4cloudException;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.product.dto.BrandDTO;
import com.mall4j.cloud.product.model.Brand;
import com.mall4j.cloud.product.service.BrandService;
import com.mall4j.cloud.product.service.CategoryBrandService;
import com.mall4j.cloud.product.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import com.mall4j.cloud.common.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * 品牌信息
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
@RestController("platformBrandController")
@RequestMapping("/platform/brand")
@Tag(name = "platform-品牌信息")
public class BrandController {

    @Autowired
    private BrandService brandService;



    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryBrandService categoryBrandService;

	@GetMapping("/page")
	@Operation(summary = "获取品牌信息列表" , description = "分页获取品牌信息列表")
	public ServerResponseEntity<PageVO<BrandVO>> page(@Valid PageDTO pageDTO, BrandDTO brandDTO) {
		PageVO<BrandVO> brandPage = brandService.page(pageDTO, brandDTO);
		return ServerResponseEntity.success(brandPage);
	}

	@GetMapping
    @Operation(summary = "获取品牌信息" , description = "根据brandId获取品牌信息")
    public ServerResponseEntity<BrandVO> getByBrandId(@RequestParam Long brandId) {
        BrandVO brand = brandService.getByBrandId(brandId);
        categoryService.getPathNames(brand.getCategories());
        return ServerResponseEntity.success(brand);
    }

    @PostMapping
    @Operation(summary = "保存品牌信息" , description = "保存品牌信息")
    public ServerResponseEntity<Void> save(@Valid @RequestBody BrandDTO brandDTO) {
        if (CollUtil.isEmpty(brandDTO.getCategoryIds())) {
            throw new Mall4cloudException("分类不能为空");
        }if (StrUtil.isEmpty(brandDTO.getName())) {
            throw new Mall4cloudException("品牌名称不能为空");
        }
        Brand brand = BeanUtil.map(brandDTO, Brand.class);
        brandService.save(brand, brandDTO.getCategoryIds());
        brandService.removeCache(brandDTO.getCategoryIds());
        return ServerResponseEntity.success();
    }

    @PutMapping
    @Operation(summary = "更新品牌信息" , description = "更新品牌信息")
    public ServerResponseEntity<Void> update(@Valid @RequestBody BrandDTO brandDTO) {
        if (CollUtil.isEmpty(brandDTO.getCategoryIds())) {
            throw new Mall4cloudException("分类不能为空");
        }
        Brand brand = BeanUtil.map(brandDTO, Brand.class);
        brandService.update(brand, brandDTO.getCategoryIds());
        // 清楚缓存
        List<Long> categoryIds = categoryBrandService.getCategoryIdBrandId(brand.getBrandId());
        categoryIds.addAll(brandDTO.getCategoryIds());
        brandService.removeCache(categoryIds);
        return ServerResponseEntity.success();
    }

    @DeleteMapping
    @Operation(summary = "删除品牌信息" , description = "根据品牌信息id删除品牌信息")
    public ServerResponseEntity<Void> delete(@RequestParam Long brandId) {
        brandService.deleteById(brandId);
        brandService.removeCache(categoryBrandService.getCategoryIdBrandId(brandId));
        return ServerResponseEntity.success();
    }

    @PutMapping(value = "/update_brand_status")
    @Operation(summary = "更新品牌状态（启用或禁用）" , description = "更新品牌状态（启用或禁用）")
    public ServerResponseEntity<Void> updateBrandStatus(@RequestBody BrandDTO brandDTO) {
	    if (Objects.isNull(brandDTO.getStatus())) {
	        throw new Mall4cloudException("状态不能为空");
        }
        if (Objects.isNull(brandDTO.getBrandId())) {
            throw new Mall4cloudException("品牌id不能为空");
        }
        brandService.updateBrandStatus(brandDTO);
        return ServerResponseEntity.success();
    }
}
