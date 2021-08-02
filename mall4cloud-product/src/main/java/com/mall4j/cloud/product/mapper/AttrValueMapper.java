package com.mall4j.cloud.product.mapper;

import com.mall4j.cloud.product.model.AttrValue;
import com.mall4j.cloud.product.model.SpuAttrValue;
import com.mall4j.cloud.product.model.SpuSkuAttrValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 属性值信息
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
public interface AttrValueMapper {

    /**
     *  批量保存属性值
     * @param attrValues
     */
    void saveBatch(@Param("attrValues") List<AttrValue> attrValues);

    /**
     * 根据 attrId 获取属性值id列表
     * @param attrId
     * @return
     */
    List<Long> getIdListByAttrId(@Param("attrId") Long attrId);

    /**
     * 批量更新
     * @param attrValues
     */
	void updateBatch(@Param("attrValues") List<AttrValue> attrValues);

    /**
     * 批量删除
     * @param attrValueIds
     */
	void deleteBatch(@Param("attrValueIds") List<Long> attrValueIds);

    /**
     * 批量更新spu中的规格数据
     * @param spuAttrValues
     */
    void updateBatchOfSpuAttrValue(@Param("spuAttrValues") List<SpuAttrValue> spuAttrValues);

    /**
     * 批量更新sku中的规格数据
     * @param spuSkuAttrValues
     */
	void updateBatchOfSpuSkuAttrValue(@Param("spuSkuAttrValues") List<SpuSkuAttrValue> spuSkuAttrValues);
}
