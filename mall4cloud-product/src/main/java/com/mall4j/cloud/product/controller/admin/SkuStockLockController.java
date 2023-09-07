package com.mall4j.cloud.product.controller.admin;

import com.mall4j.cloud.product.model.SkuStockLock;
import com.mall4j.cloud.product.service.SkuStockLockService;
import com.mall4j.cloud.product.dto.SkuStockLockDTO;

import com.mall4j.cloud.common.database.dto.PageDTO;
import com.mall4j.cloud.common.database.vo.PageVO;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import com.mall4j.cloud.common.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

/**
 * 库存锁定信息
 *
 * @author FrozenWatermelon
 * @date 2020-12-22 16:12:10
 */
@RestController("adminSkuStockLockController")
@RequestMapping("/admin/sku_stock_lock")
@Tag(name = "库存锁定信息")
public class SkuStockLockController {

    @Autowired
    private SkuStockLockService skuStockLockService;



	@GetMapping("/page")
	@Operation(summary = "获取库存锁定信息列表" , description = "分页获取库存锁定信息列表")
	public ServerResponseEntity<PageVO<SkuStockLock>> page(@Valid PageDTO pageDTO) {
		PageVO<SkuStockLock> skuStockLockPage = skuStockLockService.page(pageDTO);
		return ServerResponseEntity.success(skuStockLockPage);
	}

	@GetMapping
    @Operation(summary = "获取库存锁定信息" , description = "根据id获取库存锁定信息")
    public ServerResponseEntity<SkuStockLock> getById(@RequestParam Long id) {
        return ServerResponseEntity.success(skuStockLockService.getById(id));
    }

    @PostMapping
    @Operation(summary = "保存库存锁定信息" , description = "保存库存锁定信息")
    public ServerResponseEntity<Void> save(@Valid @RequestBody SkuStockLockDTO skuStockLockDTO) {
        SkuStockLock skuStockLock = BeanUtil.map(skuStockLockDTO, SkuStockLock.class);
        skuStockLock.setId(null);
        skuStockLockService.save(skuStockLock);
        return ServerResponseEntity.success();
    }

    @PutMapping
    @Operation(summary = "更新库存锁定信息" , description = "更新库存锁定信息")
    public ServerResponseEntity<Void> update(@Valid @RequestBody SkuStockLockDTO skuStockLockDTO) {
        SkuStockLock skuStockLock = BeanUtil.map(skuStockLockDTO, SkuStockLock.class);
        skuStockLockService.update(skuStockLock);
        return ServerResponseEntity.success();
    }

    @DeleteMapping
    @Operation(summary = "删除库存锁定信息" , description = "根据库存锁定信息id删除库存锁定信息")
    public ServerResponseEntity<Void> delete(@RequestParam Long id) {
        skuStockLockService.deleteById(id);
        return ServerResponseEntity.success();
    }
}
