package com.mall4j.cloud.product.controller.app;

import com.mall4j.cloud.product.dto.BrandDTO;
import com.mall4j.cloud.product.service.BrandService;
import com.mall4j.cloud.api.product.vo.BrandVO;
import com.mall4j.cloud.common.database.dto.PageDTO;
import com.mall4j.cloud.common.database.vo.PageVO;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

/**
 * 品牌信息
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
@RestController("appBrandController")
@RequestMapping("/ua/brand")
@Api(tags = "app-品牌信息")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("/page")
    @ApiOperation(value = "获取品牌信息列表", notes = "分页获取品牌信息列表")
    public ServerResponseEntity<PageVO<BrandVO>> page(@Valid PageDTO pageDTO, BrandDTO brandDTO) {
        PageVO<BrandVO> brandPage = brandService.page(pageDTO, brandDTO);
        return ServerResponseEntity.success(brandPage);
    }

    @GetMapping("/top_brand_list")
    @ApiOperation(value = "置顶品牌列表", notes = "置顶品牌列表")
    public ServerResponseEntity<List<BrandVO>> topBrandList() {
        List<BrandVO> brandPage = brandService.topBrandList();
        return ServerResponseEntity.success(brandPage);
    }

    @GetMapping("/list_by_category")
    @ApiOperation(value = "分类-推荐品牌信息列表", notes = "分类-推荐品牌信息列表")
    public ServerResponseEntity<List<BrandVO>> getTopBrandList(Long categoryId) {
        List<BrandVO> brandPage = brandService.listByCategory(categoryId);
        return ServerResponseEntity.success(brandPage);
    }
}
