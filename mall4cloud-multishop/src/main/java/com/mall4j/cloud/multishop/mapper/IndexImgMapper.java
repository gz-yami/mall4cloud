package com.mall4j.cloud.multishop.mapper;

import com.mall4j.cloud.multishop.dto.IndexImgDTO;
import com.mall4j.cloud.multishop.model.IndexImg;
import com.mall4j.cloud.multishop.vo.IndexImgVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 轮播图
 *
 * @author YXF
 * @date 2020-11-24 16:38:32
 */
public interface IndexImgMapper {

	/**
	 * 获取轮播图列表
	 * @param indexImgDTO
	 * @return 轮播图列表
	 */
	List<IndexImgVO> list(@Param("indexImg") IndexImgDTO indexImgDTO);

	/**
	 * 根据轮播图id获取轮播图
	 * @param imgId 轮播图id
	 * @return 轮播图
	 */
	IndexImgVO getByImgId(@Param("imgId") Long imgId);

	/**
	 * 保存轮播图
	 * @param indexImg 轮播图
	 */
	void save(@Param("indexImg") IndexImg indexImg);

	/**
	 * 更新轮播图
	 * @param indexImg 轮播图
	 */
	void update(@Param("indexImg") IndexImg indexImg);

	/**
	 * 根据轮播图id删除轮播图
	 * @param imgId 轮播图id
	 * @param shopId 店铺id
	 */
	void deleteByIdAndShopId(@Param("imgId") Long imgId, @Param("shopId") Long shopId);

	/**
	 * 根据店铺id，获取轮播图列表
	 * @param shopId
	 * @return
	 */
    List<IndexImgVO> getListByShopId(@Param("shopId") Long shopId);

	/**
	 * 根据spuId清除轮播图的spuId
	 * @param spuId
	 */
	void clearSpuIdBySpuId(Long spuId);
}
