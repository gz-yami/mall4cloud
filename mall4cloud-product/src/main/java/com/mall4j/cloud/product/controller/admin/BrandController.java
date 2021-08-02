package com.mall4j.cloud.product.controller.admin;

import com.mall4j.cloud.api.product.vo.BrandVO;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.product.service.BrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 品牌信息
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
@RestController("adminBrandController")
@RequestMapping("/admin/brand")
@Api(tags = "admin-品牌信息")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("/get_brand_by_category_id")
    @ApiImplicitParam(name = "categoryId",value = "分类id")
    @ApiOperation(value = "根据分类，获取品牌列表", notes = "根据分类，获取品牌列表")
    public ServerResponseEntity<List<BrandVO>> getBrandByCategoryId(@RequestParam Long categoryId) {
        return ServerResponseEntity.success(brandService.getBrandByCategoryId(categoryId));
    }
}
