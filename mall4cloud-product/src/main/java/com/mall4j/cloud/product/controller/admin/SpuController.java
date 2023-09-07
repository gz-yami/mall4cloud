package com.mall4j.cloud.product.controller.admin;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.mall4j.cloud.common.constant.Constant;
import com.mall4j.cloud.common.constant.StatusEnum;
import com.mall4j.cloud.common.database.dto.PageDTO;
import com.mall4j.cloud.common.database.vo.PageVO;
import com.mall4j.cloud.common.exception.Mall4cloudException;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.common.security.AuthUserContext;
import com.mall4j.cloud.product.dto.SkuDTO;
import com.mall4j.cloud.product.dto.SpuDTO;
import com.mall4j.cloud.product.dto.SpuPageSearchDTO;
import com.mall4j.cloud.product.model.SpuExtension;
import com.mall4j.cloud.product.service.*;
import com.mall4j.cloud.api.product.vo.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * spu信息
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
@RestController("platformSpuController")
@RequestMapping("/admin/spu")
@Tag(name = "admin-spu信息")
public class SpuController {

    @Autowired
    private SpuService spuService;
    @Autowired
    private SkuService skuService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AttrService attrService;
    @Autowired
    private BrandService brandService;

    @GetMapping("/platform_page")
    @Operation(summary = "获取平台spu信息列表" , description = "分页获取平台spu信息列表")
    public ServerResponseEntity<PageVO<SpuVO>> platformPage(PageDTO pageDTO, SpuPageSearchDTO spuDTO) {
        PageVO<SpuVO> spuPage = spuService.platformPage(pageDTO, spuDTO);
        return ServerResponseEntity.success(spuPage);
    }

    @GetMapping("/page")
    @Operation(summary = "获取spu信息列表" , description = "分页获取spu信息列表")
    public ServerResponseEntity<PageVO<SpuVO>> page(PageDTO pageDTO, SpuPageSearchDTO spuDTO) {
        PageVO<SpuVO> spuPage = spuService.page(pageDTO, spuDTO);
        return ServerResponseEntity.success(spuPage);
    }

    @GetMapping
    @Operation(summary = "获取spu信息" , description = "根据spuId获取spu信息")
    public ServerResponseEntity<SpuVO> getBySpuId(@RequestParam Long spuId) {
        // 获取spu信息
        SpuVO spuVO = spuService.getBySpuId(spuId);
        SpuExtension spuExtension = spuService.getSpuExtension(spuId);
        spuVO.setTotalStock(spuExtension.getActualStock());
        spuVO.setSaleNum(spuExtension.getSaleNum());
        // 品牌信息
        spuVO.setBrand(brandService.getByBrandId(spuVO.getBrandId()));
        // sku信息
        spuVO.setSkus(skuService.listBySpuIdAndExtendInfo(spuId));
        loadSpuAttrs(spuVO);
        // 平台分类、店铺分类信息
        spuVO.setCategory(categoryService.getPathNameByCategoryId(spuVO.getCategoryId()));
        spuVO.setShopCategory(categoryService.getPathNameByCategoryId(spuVO.getShopCategoryId()));
        return ServerResponseEntity.success(spuVO);
    }


    @PostMapping
    @Operation(summary = "保存spu信息" , description = "保存spu信息")
    public ServerResponseEntity<Void> save(@Valid @RequestBody SpuDTO spuDTO) {
        checkSaveOrUpdateInfo(spuDTO);
        spuService.save(spuDTO);
        return ServerResponseEntity.success();
    }

    @PutMapping
    @Operation(summary = "更新spu信息" , description = "更新spu信息")
    public ServerResponseEntity<Void> update(@Valid @RequestBody SpuDTO spuDTO) {
        checkSaveOrUpdateInfo(spuDTO);
        List<Long> skuIds = spuDTO.getSkuList().stream().filter(sku -> Objects.nonNull(sku.getSkuId())).map(SkuDTO::getSkuId).collect(Collectors.toList());
        spuService.update(spuDTO);
        // 清除缓存
        spuService.removeSpuCacheBySpuId(spuDTO.getSpuId());
        skuService.removeSkuCacheBySpuIdOrSkuIds(spuDTO.getSpuId(), skuIds);
        return ServerResponseEntity.success();
    }


    @DeleteMapping
    @Operation(summary = "删除spu信息" , description = "根据spu信息id删除spu信息")
    public ServerResponseEntity<Void> delete(@RequestParam Long spuId) {
        spuService.deleteById(spuId);
        // 清除缓存
        spuService.removeSpuCacheBySpuId(spuId);
        skuService.removeSkuCacheBySpuIdOrSkuIds(spuId, null);
        return ServerResponseEntity.success();
    }

    @PutMapping("/update_spu_data")
    @Operation(summary = "修改spu（名称、价格、库存、序号）信息" , description = "更新spu信息")
    public ServerResponseEntity<Void> updateSpuData(@RequestBody SpuDTO spuDTO) {
        spuService.updateSpuOrSku(spuDTO);
        // 清除缓存
        spuService.removeSpuCacheBySpuId(spuDTO.getSpuId());
        skuService.removeSkuCacheBySpuIdOrSkuIds(spuDTO.getSpuId(), null);
        return ServerResponseEntity.success();
    }


    /**
     * 更新商品状态
     */
    @PutMapping("/prod_status")
    @Operation(summary = "商品上下架" , description = "商品上下架")
    public ServerResponseEntity<Void> spuChangeStatus(@RequestBody SpuDTO spuDTO) {
        if (Objects.nonNull(spuDTO.getSpuId())) {
            spuUpdateStatus(spuDTO);
        } else if (CollUtil.isNotEmpty(spuDTO.getSpuIds())) {
            spuBatchUpdateStatus(spuDTO);
        }
        return ServerResponseEntity.success();
    }

    /**
     * spu上下架
     * @param spu
     */
    private void spuUpdateStatus(SpuDTO spu) {
        SpuVO dbSpu = spuService.getBySpuId(spu.getSpuId());
        String error = checkUpdateStatusData(dbSpu);
        if (StrUtil.isNotBlank(error)) {
            throw new Mall4cloudException(error);
        }
        spuService.changeSpuStatus(spu.getSpuId(), spu.getStatus());
        spuService.removeSpuCacheBySpuId(spu.getSpuId());
    }

    /**
     * spu批量上下架
     * @param spu
     */
    private void spuBatchUpdateStatus(SpuDTO spu) {
        List<Long> spuIds = new ArrayList<>(spu.getSpuIds());
        List<Long> errorList = new ArrayList<>(spu.getSpuIds());
        List<SpuVO> spuList = spuService.listBySpuIds(spu.getSpuIds(), null, null);
        if (CollUtil.isEmpty(spuList)) {
            throw new Mall4cloudException("您选择的商品信息有误，请刷新后重试");
        }
        Map<Long, SpuVO> spuMap = spuList.stream().collect(Collectors.toMap(SpuVO::getSpuId, s -> s));
        for (Long spuId : spu.getSpuIds()) {
            String errorInfo = checkUpdateStatusData(spuMap.get(spuId));
            if (StrUtil.isNotBlank(errorInfo)) {
                spuIds.remove(spuId);
                errorList.add(spuId);
            }
        }
        if (CollUtil.isEmpty(spuIds)) {
            throw new Mall4cloudException("您所选择的商品中没有符合操作条件的商品");
        }
        spuService.batchRemoveSpuCacheBySpuId(spuIds);
        if (errorList.size() > 0) {
            throw new Mall4cloudException("商品id为：" + errorList.toString() + "的" + errorList.size() + "件商品不符合操作条件");
        }
        spuService.changeSpuStatus(spu.getSpuId(), spu.getStatus());
        spuService.removeSpuCacheBySpuId(spu.getSpuId());
    }

    /**
     * 加载spu属性列表
     * @param spuVO
     */
    private void loadSpuAttrs(SpuVO spuVO) {
        Map<Long, SpuAttrValueVO> attrMap = null;
        if (CollUtil.isNotEmpty(spuVO.getSpuAttrValues())) {
            attrMap = spuVO.getSpuAttrValues().stream().collect(Collectors.toMap(SpuAttrValueVO::getAttrId, s -> s));
        } else {
             attrMap = new HashMap<>(1);
        }
        List<AttrVO> attrList = attrService.getAttrsByCategoryIdAndAttrType(spuVO.getCategoryId());
        List<SpuAttrValueVO> spuAttrValues = new ArrayList<>();
        for (AttrVO attrVO : attrList) {
            SpuAttrValueVO spuAttrValueVO = attrMap.get(attrVO.getAttrId());
            SpuAttrValueVO newSpuAttrValue = new SpuAttrValueVO();
            if (Objects.nonNull(spuAttrValueVO)) {
                Boolean hasValue = false;
                for (AttrValueVO attrValue : attrVO.getAttrValues()) {
                    if (Objects.equals(attrValue.getAttrValueId(), spuAttrValueVO.getAttrValueId())) {
                        hasValue = true;
                    }
                }
                if (hasValue || CollUtil.isEmpty(attrVO.getAttrValues())) {
                    spuAttrValues.add(spuAttrValueVO);
                    continue;
                }
                newSpuAttrValue.setSpuAttrValueId(spuAttrValueVO.getSpuAttrValueId());
            }
            newSpuAttrValue.setAttrId(attrVO.getAttrId());
            newSpuAttrValue.setAttrName(attrVO.getName());
            newSpuAttrValue.setSearchType(attrVO.getSearchType());
            spuAttrValues.add(newSpuAttrValue);
        }
        spuVO.setSpuAttrValues(spuAttrValues);
    }

    /**
     * 校验spu新增或更新信息
     * @param spuDTO
     */
    private void checkSaveOrUpdateInfo(SpuDTO spuDTO) {
        if (!Objects.equals(Constant.PLATFORM_SHOP_ID, AuthUserContext.get().getTenantId()) && Objects.isNull(spuDTO.getShopCategoryId())) {
            throw new Mall4cloudException("店铺分类不能为空");
        }
        if (Objects.isNull(spuDTO.getCategoryId())) {
            throw new Mall4cloudException("平台分类不能为空");
        }
        if (Objects.isNull(spuDTO.getSeq())) {
            spuDTO.setSeq(0);
        }
        if (Objects.isNull(spuDTO.getBrandId())) {
            spuDTO.setBrandId(0L);
        }
    }

    /**
     * 校验spu上下架信息
     * @param spu
     * @return
     */
    private String checkUpdateStatusData(SpuVO spu) {
        Long shopId = AuthUserContext.get().getTenantId();
        if (Objects.isNull(spu)) {
            return "查找不到该商品信息";
        }
        if (!Objects.equals(spu.getShopId(), shopId)) {
            return "查找不到该商品信息";
        }
        if (!(Objects.equals(spu.getStatus(), StatusEnum.ENABLE.value())
                || Objects.equals(spu.getStatus(), StatusEnum.DISABLE.value()))) {
            return "商品状态异常，清刷新后重试";
        }
        if(Objects.equals(spu.getStatus(),StatusEnum.ENABLE.value())){
            CategoryVO category = categoryService.getById(spu.getCategoryId());
            if (Objects.equals(category.getStatus(), StatusEnum.DISABLE.value())){
                return "该商品所属的平台分类处于下线中，商品不能上架，请联系管理员后再进行操作";            }

            if (Objects.equals(Constant.PLATFORM_SHOP_ID, AuthUserContext.get().getTenantId())) {
                CategoryVO shopCategory = categoryService.getById(spu.getShopCategoryId());
                if (Objects.equals(category.getStatus(), StatusEnum.DISABLE.value())){
                    return "该商品所属的店铺分类禁用中，商品不能进行上架操作";
                }
            }
        }
        return null;
    }


}
