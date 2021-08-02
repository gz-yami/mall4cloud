package com.mall4j.cloud.multishop.service;

import com.mall4j.cloud.api.multishop.bo.EsShopDetailBO;
import com.mall4j.cloud.common.database.dto.PageDTO;
import com.mall4j.cloud.common.database.vo.PageVO;
import com.mall4j.cloud.multishop.dto.ShopDetailDTO;
import com.mall4j.cloud.multishop.model.ShopDetail;
import com.mall4j.cloud.api.multishop.vo.ShopDetailVO;
import com.mall4j.cloud.multishop.vo.ShopDetailAppVO;

import java.util.List;

/**
 * 店铺详情
 *
 * @author FrozenWatermelon
 * @date 2020-12-05 15:50:25
 */
public interface ShopDetailService {

	/**
	 * 分页获取店铺详情列表
	 *
	 * @param pageDTO 分页参数
	 * @param shopDetailDTO 店铺搜索数据
	 * @return 店铺详情列表分页数据
	 */
	PageVO<ShopDetailVO> page(PageDTO pageDTO, ShopDetailDTO shopDetailDTO);

	/**
	 * 根据店铺详情id获取店铺详情
	 *
	 * @param shopId 店铺详情id
	 * @return 店铺详情
	 */
	ShopDetailVO getByShopId(Long shopId);

	/**
	 * 保存店铺详情
	 * @param shopDetail 店铺详情
	 */
	void save(ShopDetail shopDetail);

	/**
	 * 更新店铺详情
	 * @param shopDetail 店铺详情
	 */
	void update(ShopDetail shopDetail);

	/**
	 * 根据店铺详情id删除店铺详情
	 * @param shopId
	 */
	void deleteById(Long shopId);

    /**
     * 获取店铺列表信息
     * @param shopIds
     * @return
     */
    List<ShopDetail> listByShopIds(List<Long> shopIds);

	/**
	 * 申请开店
	 *
	 * @param shopDetailDTO 申请开店信息
	 */
	void applyShop(ShopDetailDTO shopDetailDTO);

	/**
	 * 店铺搜索分页
	 *
	 * @param pageDTO 分页参数
	 * @param shopDetailDTO 店铺信息
	 * @return 店铺信息列表
	 */
	PageVO<ShopDetailAppVO> shopSearchPage(PageDTO pageDTO, ShopDetailDTO shopDetailDTO);

	/**
	 * 改变店铺状态
	 *
	 * @param shopId
	 * @param shopStatus
	 */
	void changeSpuStatus(Long shopId, Integer shopStatus);

	/**
	 * 删除店铺缓存
	 *
	 * @param shopId
	 */
	void removeCacheByShopId(Long shopId);

	/**
	 * 获取店铺信息及扩展信息
	 *
	 * @param shopId
	 * @return
	 */
	EsShopDetailBO shopExtensionData(Long shopId);

	/**
	 * 创建店铺
	 *
	 * @param shopDetailDTO
	 */
    void createShop(ShopDetailDTO shopDetailDTO);

	/**
	 * 获取店铺信息及扩展信息
	 * @param shopIds 店铺ids
	 * @param shopName 店铺名称
	 * @return 店铺信息列表
	 */
	List<ShopDetailVO> getShopDetailByShopIdAndShopName(List<Long> shopIds, String shopName);

	/**
	 * 验证店铺名称是否重名
	 * @param shopName
	 * @return
	 */
    Boolean checkShopName(String shopName);
}
