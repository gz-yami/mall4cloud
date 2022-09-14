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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
@Api(tags = "platform-品牌信息")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Autowired
	private MapperFacade mapperFacade;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryBrandService categoryBrandService;

	@GetMapping("/page")
	@ApiOperation(value = "获取品牌信息列表", notes = "分页获取品牌信息列表")
	public ServerResponseEntity<PageVO<BrandVO>> page(@Valid PageDTO pageDTO, BrandDTO brandDTO) {
		PageVO<BrandVO> brandPage = brandService.page(pageDTO, brandDTO);
		return ServerResponseEntity.success(brandPage);
	}

	@GetMapping
    @ApiOperation(value = "获取品牌信息", notes = "根据brandId获取品牌信息")
    public ServerResponseEntity<BrandVO> getByBrandId(@RequestParam Long brandId) {
        BrandVO brand = brandService.getByBrandId(brandId);
        categoryService.getPathNames(brand.getCategories());
        return ServerResponseEntity.success(brand);
    }

    @PostMapping
    @ApiOperation(value = "保存品牌信息", notes = "保存品牌信息")
    public ServerResponseEntity<Void> save(@Valid @RequestBody BrandDTO brandDTO) {
        if (CollUtil.isEmpty(brandDTO.getCategoryIds())) {
            throw new Mall4cloudException("分类不能为空");
        }if (StrUtil.isEmpty(brandDTO.getName())) {
            throw new Mall4cloudException("品牌名称不能为空");
        }
        Brand brand = mapperFacade.map(brandDTO, Brand.class);
        brandService.save(brand, brandDTO.getCategoryIds());
        brandService.removeCache(brandDTO.getCategoryIds());
        return ServerResponseEntity.success();
    }

    @PutMapping
    @ApiOperation(value = "更新品牌信息", notes = "更新品牌信息")
    public ServerResponseEntity<Void> update(@Valid @RequestBody BrandDTO brandDTO) {
        if (CollUtil.isEmpty(brandDTO.getCategoryIds())) {
            throw new Mall4cloudException("分类不能为空");
        }
        Brand brand = mapperFacade.map(brandDTO, Brand.class);
        brandService.update(brand, brandDTO.getCategoryIds());
        // 清楚缓存
        List<Long> categoryIds = categoryBrandService.getCategoryIdBrandId(brand.getBrandId());
        categoryIds.addAll(brandDTO.getCategoryIds());
        brandService.removeCache(categoryIds);
        return ServerResponseEntity.success();
    }

    @DeleteMapping
    @ApiOperation(value = "删除品牌信息", notes = "根据品牌信息id删除品牌信息")
    public ServerResponseEntity<Void> delete(@RequestParam Long brandId) {
        brandService.deleteById(brandId);
        brandService.removeCache(categoryBrandService.getCategoryIdBrandId(brandId));
        return ServerResponseEntity.success();
    }

    @PutMapping(value = "/update_brand_status")
    @ApiOperation(value = "更新品牌状态（启用或禁用）", notes = "更新品牌状态（启用或禁用）")
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
