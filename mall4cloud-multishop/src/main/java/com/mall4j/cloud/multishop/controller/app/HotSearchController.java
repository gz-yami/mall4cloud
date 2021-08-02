package com.mall4j.cloud.multishop.controller.app;

import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.multishop.service.HotSearchService;
import com.mall4j.cloud.multishop.vo.HotSearchVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 热搜
 *
 * @author YXF
 * @date 2021-01-27 09:10:00
 */
@RestController("appHotSearchController")
@RequestMapping("/ua/app/hot_search")
@Api(tags = "app-热搜")
public class HotSearchController {

    @Autowired
    private HotSearchService hotSearchService;

	@GetMapping("/list")
	@ApiOperation(value = "获取热搜列表", notes = "获取热搜列表")
	@ApiImplicitParam(name = "shopId", value = "店铺id", defaultValue = "0")
	public ServerResponseEntity<List<HotSearchVO>> listByShopId(@RequestParam("shopId") Long shopId) {
		List<HotSearchVO> hotSearches = hotSearchService.listByShopId(shopId);
		return ServerResponseEntity.success(hotSearches);
	}
}
