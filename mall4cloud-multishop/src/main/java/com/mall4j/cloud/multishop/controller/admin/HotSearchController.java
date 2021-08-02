package com.mall4j.cloud.multishop.controller.admin;

import com.mall4j.cloud.multishop.model.HotSearch;
import com.mall4j.cloud.multishop.service.HotSearchService;
import com.mall4j.cloud.multishop.vo.HotSearchVO;
import com.mall4j.cloud.multishop.dto.HotSearchDTO;

import com.mall4j.cloud.common.database.dto.PageDTO;
import com.mall4j.cloud.common.database.vo.PageVO;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.common.security.AuthUserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/**
 * 热搜
 *
 * @author YXF
 * @date 2021-01-27 09:10:00
 */
@RestController("adminHotSearchController")
@RequestMapping("/admin/hot_search")
@Api(tags = "admin-热搜")
public class HotSearchController {

    @Autowired
    private HotSearchService hotSearchService;

    @Autowired
	private MapperFacade mapperFacade;

	@GetMapping("/page")
	@ApiOperation(value = "分页获取热搜列表", notes = "分页获取热搜列表")
	public ServerResponseEntity<PageVO<HotSearchVO>> page(@Valid PageDTO pageDTO, HotSearchDTO hotSearchDTO) {
	    hotSearchDTO.setShopId(AuthUserContext.get().getTenantId());
		PageVO<HotSearchVO> hotSearchPage = hotSearchService.page(pageDTO, hotSearchDTO);
		return ServerResponseEntity.success(hotSearchPage);
	}

	@GetMapping
    @ApiOperation(value = "获取热搜", notes = "根据hotSearchId获取热搜")
    public ServerResponseEntity<HotSearchVO> getByHotSearchId(@RequestParam Long hotSearchId) {
        return ServerResponseEntity.success(hotSearchService.getByHotSearchId(hotSearchId));
    }

    @PostMapping
    @ApiOperation(value = "保存热搜", notes = "保存热搜")
    public ServerResponseEntity<Void> save(@Valid @RequestBody HotSearchDTO hotSearchDTO) {
        HotSearch hotSearch = mapperFacade.map(hotSearchDTO, HotSearch.class);
        hotSearch.setShopId(AuthUserContext.get().getTenantId());
        hotSearchService.save(hotSearch);
        hotSearchService.removeHotSearchListCache(hotSearch.getShopId());
        return ServerResponseEntity.success();
    }

    @PutMapping
    @ApiOperation(value = "更新热搜", notes = "更新热搜")
    public ServerResponseEntity<Void> update(@Valid @RequestBody HotSearchDTO hotSearchDTO) {
        HotSearch hotSearch = mapperFacade.map(hotSearchDTO, HotSearch.class);
        hotSearch.setShopId(AuthUserContext.get().getTenantId());
        hotSearchService.update(hotSearch);
        hotSearchService.removeHotSearchListCache(hotSearch.getShopId());
        return ServerResponseEntity.success();
    }

    @DeleteMapping
    @ApiOperation(value = "删除热搜", notes = "根据热搜id删除热搜")
    public ServerResponseEntity<Void> delete(@RequestParam Long hotSearchId) {
        Long shopId = AuthUserContext.get().getTenantId();
        hotSearchService.deleteById(hotSearchId, shopId);
        hotSearchService.removeHotSearchListCache(shopId);
        return ServerResponseEntity.success();
    }
}
