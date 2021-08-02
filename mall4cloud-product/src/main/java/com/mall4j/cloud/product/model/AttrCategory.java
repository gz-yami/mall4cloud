package com.mall4j.cloud.product.model;

import java.io.Serializable;

import com.mall4j.cloud.common.model.BaseModel;
/**
 * 属性与属性分组关联信息
 *
 * @author YXF
 * @date 2020-11-23 16:20:01
 */
public class AttrCategory extends BaseModel implements Serializable{
    private static final long serialVersionUID = 1L;

    /**
     * 属性与分类关联id
     */
    private Long attrCategoryId;

    /**
     * 分类id
     */
    private Long categoryId;

    /**
     * 属性id
     */
    private Long attrId;

	public Long getAttrCategoryId() {
		return attrCategoryId;
	}

	public void setAttrCategoryId(Long attrCategoryId) {
		this.attrCategoryId = attrCategoryId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getAttrId() {
		return attrId;
	}

	public void setAttrId(Long attrId) {
		this.attrId = attrId;
	}

	@Override
	public String toString() {
		return "AttrCategory{" +
				"attrCategoryId=" + attrCategoryId +
				",createTime=" + createTime +
				",updateTime=" + updateTime +
				",categoryId=" + categoryId +
				",attrId=" + attrId +
				'}';
	}
}
