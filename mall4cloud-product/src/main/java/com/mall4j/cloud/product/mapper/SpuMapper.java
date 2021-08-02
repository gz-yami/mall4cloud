package com.mall4j.cloud.product.mapper;

import com.mall4j.cloud.api.product.bo.EsProductBO;
import com.mall4j.cloud.common.database.dto.PageDTO;
import com.mall4j.cloud.product.dto.SpuDTO;
import com.mall4j.cloud.product.dto.SpuPageSearchDTO;
import com.mall4j.cloud.product.model.Spu;
import com.mall4j.cloud.api.product.vo.SpuVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * spu信息
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
public interface SpuMapper {

	/**
	 * 获取spu信息列表
	 *
	 * @param spu
	 * @return spu信息列表
	 */
	List<SpuVO> list(@Param("spu") SpuPageSearchDTO spu);

	/**
	 * 根据spu信息id获取spu信息
	 *
	 * @param spuId spu信息id
	 * @return spu信息
	 */
	SpuVO getBySpuId(@Param("spuId") Long spuId);

	/**
	 * 保存spu信息
	 *
	 * @param spu spu信息
	 */
	void save(@Param("spu") Spu spu);

	/**
	 * 更新spu信息
	 *
	 * @param spu spu信息
	 */
	void update(@Param("spu") Spu spu);

	/**
	 * 根据spu信息id删除spu信息
	 *
	 * @param spuId
	 */
	void deleteById(@Param("spuId") Long spuId);

	/**
	 * 根据商品id修改商品状态
	 *
	 * @param spuId 商品id
	 */
	void updateStatusBySpuId(@Param("spuId") Long spuId);

	/**
	 * 根据spuId获取商品信息（搜索）
	 *
	 * @param spuId
	 * @return 商品信息
	 */
	EsProductBO loadEsProductBO(@Param("spuId") Long spuId);

	/**
	 * 获取 spuId列表
	 *
	 * @param shopCategoryIds 店铺分类id列表
	 * @param categoryIds 平台分类Id列表
	 * @param brandId 品牌id
	 * @param shopId 店铺id
	 * @return spuId列表
	 */
	List<Long> getSpuIdsBySpuUpdateDTO(@Param("shopCategoryIds") List<Long> shopCategoryIds, @Param("categoryIds") List<Long> categoryIds
			, @Param("brandId") Long brandId, @Param("shopId") Long shopId);

	/**
	 * 改变商品状态（上下架）
	 *
	 * @param spuId
	 * @param status
	 */
	void changeSpuStatus(@Param("spuId") Long spuId, @Param("status") Integer status);

	/**
	 * 更新spu表（canal监听后，会发送更新的消息，更新es中的数据）
	 *
	 * @param spuIds
	 * @param categoryIds
	 */
    void updateSpuUpdateTime(@Param("spuIds") List<Long> spuIds, @Param("categoryIds") List<Long> categoryIds);

	/**
	 * 根据店铺id获取spu列表
	 * @param spuIds 商品ids
	 * @param spuName 商品名称
	 * @param isFailure 是否失效
	 * @return 商品列表信息
	 */
	List<SpuVO> listBySpuIds(@Param("spuIds") List<Long> spuIds, @Param("spuName") String spuName, @Param("isFailure") Integer isFailure);

	/**
	 * 根据分组id和是否为当前分组，返回商品列表
	 * @param pageDTO 分页信息
	 * @param spuDTO 分组信息
	 * @param isContain 是否当前分组商品 1.参与 0.不参与
	 * @return 商品列表
	 */
    List<SpuVO> pageByLangAndTagId(@Param("pageDTO") PageDTO pageDTO, @Param("spuDTO") SpuDTO spuDTO, @Param("isContain") Integer isContain);

	/**
	 * 根据分类id列表批量获取商品id列表
	 * @param cidList
	 * @param type
	 * @param shopId
	 * @return
	 */
    List<Long> listIdsByCidListAndTypeAndShopId(@Param("cidList") List<Long> cidList, @Param("type") int type, @Param("shopId") Long shopId);

	/**
	 * 根据商品id列表与状态批量修改商品状态
	 * @param spuIdList
	 * @param status
	 */
	void batchChangeSpuStatusBySpuIdsAndStatus(@Param("spuIdList") List<Long> spuIdList, @Param("status") Integer status);
}
