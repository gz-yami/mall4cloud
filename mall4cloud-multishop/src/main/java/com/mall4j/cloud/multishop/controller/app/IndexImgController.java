package com.mall4j.cloud.multishop.controller.app;

import com.mall4j.cloud.multishop.service.IndexImgService;
import com.mall4j.cloud.multishop.vo.IndexImgVO;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 轮播图
 *
 * @author YXF
 * @date 2020-11-24 16:38:32
 */
@RestController("appIndexImgController")
@RequestMapping("/ua/index_img")
@Api(tags = "app-轮播图")
public class IndexImgController {

    @Autowired
    private IndexImgService indexImgService;

	@GetMapping("/list")
	@ApiOperation(value = "获取轮播图列表", notes = "分页获取轮播图列表")
	@ApiImplicitParam(name = "shopId", value = "店铺id（平台：0）", defaultValue = "0")
	public ServerResponseEntity<List<IndexImgVO>> getList(@RequestParam("shopId") Long shopId) {
		List<IndexImgVO> indexImgPage = indexImgService.getListByShopId(shopId);
		return ServerResponseEntity.success(indexImgPage);
	}
}
