package com.mall4j.cloud.product.model;

import com.mall4j.cloud.common.model.BaseModel;

import java.io.Serializable;

/**
 * 商品规格属性关联信息
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
public class SpuAttrValue extends BaseModel implements Serializable{
    private static final long serialVersionUID = 1L;

    /**
     * 商品属性值关联信息id
     */
    private Long spuAttrValueId;

    /**
     * 商品id
     */
    private Long spuId;

    /**
     * 规格属性id
     */
    private Long attrId;

    /**
     * 规格属性名称
     */
    private String attrName;

    /**
     * 规格属性值id
     */
    private Long attrValueId;

	/**
	 * 规格属性值名称
	 */
	private String attrValueName;

	/**
	 * 规格属性描述
	 */
	private String attrDesc;

	public Long getSpuAttrValueId() {
		return spuAttrValueId;
	}

	public void setSpuAttrValueId(Long spuAttrValueId) {
		this.spuAttrValueId = spuAttrValueId;
	}

	public Long getSpuId() {
		return spuId;
	}

	public void setSpuId(Long spuId) {
		this.spuId = spuId;
	}

	public Long getAttrId() {
		return attrId;
	}

	public void setAttrId(Long attrId) {
		this.attrId = attrId;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public Long getAttrValueId() {
		return attrValueId;
	}

	public void setAttrValueId(Long attrValueId) {
		this.attrValueId = attrValueId;
	}

	public String getAttrValueName() {
		return attrValueName;
	}

	public void setAttrValueName(String attrValueName) {
		this.attrValueName = attrValueName;
	}

	public String getAttrDesc() {
		return attrDesc;
	}

	public void setAttrDesc(String attrDesc) {
		this.attrDesc = attrDesc;
	}

	@Override
	public String toString() {
		return "SpuAttrValue{" +
				"spuAttrValueId=" + spuAttrValueId +
				",spuId=" + spuId +
				",attrId=" + attrId +
				",attrName=" + attrName +
				",attrValueId=" + attrValueId +
				",attrValueName=" + attrValueName +
				'}';
	}
}
