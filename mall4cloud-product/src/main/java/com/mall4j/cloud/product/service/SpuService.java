package com.mall4j.cloud.product.service;

import com.mall4j.cloud.api.product.bo.EsProductBO;
import com.mall4j.cloud.common.database.dto.PageDTO;
import com.mall4j.cloud.common.database.vo.PageVO;
import com.mall4j.cloud.api.product.vo.SpuVO;
import com.mall4j.cloud.product.dto.SpuDTO;
import com.mall4j.cloud.product.dto.SpuPageSearchDTO;
import com.mall4j.cloud.product.model.SpuExtension;

import java.util.List;

/**
 * spu信息
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
public interface SpuService {

	/**
	 * 分页获取spu信息列表
	 * @param pageDTO 分页参数
	 * @param spuDTO
	 * @return spu信息列表分页数据
	 */
	PageVO<SpuVO> page(PageDTO pageDTO, SpuPageSearchDTO spuDTO);

	/**
	 * spu基本信息
	 *
	 * @param spuId spu信息id
	 * @return spu信息
	 */
	SpuVO getBySpuId(Long spuId);

	/**
	 * spu扩展信息
	 *
	 * @param spuId spu信息id
	 * @return spu信息
	 */
	SpuExtension getSpuExtension(Long spuId);

	/**
	 * 保存spu信息
	 * @param spuDTO spu信息
	 */
	void save(SpuDTO spuDTO);

	/**
	 * 更新spu信息
	 * @param spuDTO spu信息
	 */
	void update(SpuDTO spuDTO);

	/**
	 * 根据spu信息id删除spu信息
	 * @param spuId
	 */
	void deleteById(Long spuId);

	/**
	 * 根据spuId清除缓存
	 * @param spuId id
	 */
	void removeSpuCacheBySpuId(Long spuId);

	/**
	 * 根据spuId列表， 批量清除缓存
	 * @param spuIds id
	 */
	void batchRemoveSpuCacheBySpuId(List<Long> spuIds);

	/**
	 * 改变商品状态（上下架）
	 * @param spuId
	 * @param status
	 */
	void changeSpuStatus(Long spuId, Integer status);

	/**
	 * 更新商品的信息
	 * @param spuDTO
	 */
	void updateSpuOrSku(SpuDTO spuDTO);

	/**
	 * 更新spu表（canal监听后，会发送更新的消息，更新es中的数据）
	 * @param spuIds
	 * @param categoryIds
	 */
    void updateSpuUpdateTime(List<Long> spuIds, List<Long> categoryIds);

	/**
	 * 根据spuId获取商品信息
	 * @param spuId
	 * @return 商品信息
	 */
	EsProductBO loadEsProductBO(Long spuId);

	/**
	 * 获取 spuId列表
	 * @param shopCategoryIds 店铺分类id列表
	 * @param categoryIds 平台分类Id列表
	 * @param brandId 品牌id
	 * @param shopId 店铺id
	 * @return spuId列表
	 */
	List<Long> getSpuIdsBySpuUpdateDTO(List<Long> shopCategoryIds, List<Long> categoryIds, Long brandId, Long shopId);

	/**
	 * 根据spuId列表，获取spu列表
	 * @param spuIds
	 * @param prodName
	 * @param isFailure
	 * @return spu列表
	 */
	List<SpuVO> listBySpuIds(List<Long> spuIds, String prodName, Integer isFailure);

	/**
	 * 获取平台分页数据
	 * @param pageDTO
	 * @param spuDTO
	 * @return
	 */
	PageVO<SpuVO> platformPage(PageDTO pageDTO, SpuPageSearchDTO spuDTO);

	/**
	 * 根据分组id和是否为当前分组，返回商品列表
	 * @param pageDTO 分页信息
	 * @param spuDTO 分组信息
	 * @param isContain 是否当前分组商品 1.参与 0.不参与
	 * @return 商品列表
	 */
	PageVO<SpuVO> pageByLangAndTagId(PageDTO pageDTO, SpuDTO spuDTO, Integer isContain);

	/**
	 * 批量修改商品状态
	 * @param cidList
	 * @param shopId
	 * @param status
	 */
    void batchChangeSpuStatusByCids(List<Long> cidList, Long shopId, Integer status);
}
