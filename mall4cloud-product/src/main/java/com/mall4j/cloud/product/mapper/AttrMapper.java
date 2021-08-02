package com.mall4j.cloud.product.mapper;

import com.mall4j.cloud.common.database.util.PageAdapter;
import com.mall4j.cloud.product.dto.AttrDTO;
import com.mall4j.cloud.product.model.Attr;
import com.mall4j.cloud.api.product.vo.AttrVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 属性信息
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:23
 */
public interface AttrMapper {

	/**
	 * 获取属性信息列表
	 *
	 * @param pageAdapter 分页参数
	 * @param attrDTO 属性数据
	 * @return 属性信息列表
	 */
	List<AttrVO> list(@Param("page") PageAdapter pageAdapter, @Param("attr") AttrDTO attrDTO);
	/**
	 * 获取属性总数
	 *
	 * @param attrDTO
	 * @return
	 */
	Long countAttr(@Param("attr") AttrDTO attrDTO);

	/**
	 * 根据属性信息id获取属性信息
	 *
	 * @param attrId 属性信息id
	 * @return 属性信息
	 */
	AttrVO getByAttrId(@Param("attrId") Long attrId);

	/**
	 * 保存属性信息
	 *
	 * @param attr 属性信息
	 */
	void save(@Param("attr") Attr attr);

	/**
	 * 更新属性信息
	 *
	 * @param attr 属性信息
	 */
	void update(@Param("attr") Attr attr);

	/**
	 * 根据属性信息id删除属性信息
	 *
	 * @param attrId
	 */
	void deleteById(@Param("attrId") Long attrId);

	/**
	 * 根据分类和属性类型，获取对应的属性列表
	 *
	 * @param categoryId
	 * @return
	 */
	List<AttrVO> getAttrsByCategoryIdAndAttrType(@Param("categoryId") Long categoryId);

	/**
	 * 获取店铺中的销售属性
	 * @param shopId
	 * @return 销售属性列表
	 */
	List<AttrVO> getShopAttrs(Long shopId);
}
