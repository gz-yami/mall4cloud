package com.mall4j.cloud.product.controller.app;

import com.mall4j.cloud.product.model.SkuStockLock;
import com.mall4j.cloud.product.service.SkuStockLockService;
import com.mall4j.cloud.product.dto.SkuStockLockDTO;

import com.mall4j.cloud.common.database.dto.PageDTO;
import com.mall4j.cloud.common.database.vo.PageVO;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/**
 * 库存锁定信息
 *
 * @author FrozenWatermelon
 * @date 2020-12-22 16:12:10
 */
@RestController("appSkuStockLockController")
@RequestMapping("/a/sku_stock_lock")
@Api(tags = "库存锁定信息")
public class SkuStockLockController {

    @Autowired
    private SkuStockLockService skuStockLockService;

    @Autowired
	private MapperFacade mapperFacade;

	@GetMapping("/page")
	@ApiOperation(value = "获取库存锁定信息列表", notes = "分页获取库存锁定信息列表")
	public ServerResponseEntity<PageVO<SkuStockLock>> page(@Valid PageDTO pageDTO) {
		PageVO<SkuStockLock> skuStockLockPage = skuStockLockService.page(pageDTO);
		return ServerResponseEntity.success(skuStockLockPage);
	}

	@GetMapping
    @ApiOperation(value = "获取库存锁定信息", notes = "根据id获取库存锁定信息")
    public ServerResponseEntity<SkuStockLock> getById(@RequestParam Long id) {
        return ServerResponseEntity.success(skuStockLockService.getById(id));
    }

    @PostMapping
    @ApiOperation(value = "保存库存锁定信息", notes = "保存库存锁定信息")
    public ServerResponseEntity<Void> save(@Valid @RequestBody SkuStockLockDTO skuStockLockDTO) {
        SkuStockLock skuStockLock = mapperFacade.map(skuStockLockDTO, SkuStockLock.class);
        skuStockLockService.save(skuStockLock);
        return ServerResponseEntity.success();
    }

    @PutMapping
    @ApiOperation(value = "更新库存锁定信息", notes = "更新库存锁定信息")
    public ServerResponseEntity<Void> update(@Valid @RequestBody SkuStockLockDTO skuStockLockDTO) {
        SkuStockLock skuStockLock = mapperFacade.map(skuStockLockDTO, SkuStockLock.class);
        skuStockLockService.update(skuStockLock);
        return ServerResponseEntity.success();
    }

    @DeleteMapping
    @ApiOperation(value = "删除库存锁定信息", notes = "根据库存锁定信息id删除库存锁定信息")
    public ServerResponseEntity<Void> delete(@RequestParam Long id) {
        skuStockLockService.deleteById(id);
        return ServerResponseEntity.success();
    }
}
