package com.mall4j.cloud.product.model;

import java.io.Serializable;
import java.util.List;

import com.mall4j.cloud.common.model.BaseModel;

/**
 * 属性信息
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:23
 */
public class Attr extends BaseModel implements Serializable{
    private static final long serialVersionUID = 1L;


	/**
     * attr id
     */
    private Long attrId;

	/**
	 * 店铺id
	 */
	private Long shopId;

    /**
     * 属性名称
     */
    private String name;

    /**
     * 属性描述
     */
    private String desc;

	/**
	 * 作为搜索参数 0:不需要，1:需要
	 */
	private Integer searchType;

	/**
	 * 属性类型 0:销售属性，1:基本属性
	 */
	private Integer attrType;

	/**
	 * 属性值列表
	 */
	private List<AttrValue> attrValues;

	public Long getAttrId() {
		return attrId;
	}

	public void setAttrId(Long attrId) {
		this.attrId = attrId;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getSearchType() {
		return searchType;
	}

	public void setSearchType(Integer searchType) {
		this.searchType = searchType;
	}

	public Integer getAttrType() {
		return attrType;
	}

	public void setAttrType(Integer attrType) {
		this.attrType = attrType;
	}

	public List<AttrValue> getAttrValues() {
		return attrValues;
	}

	public void setAttrValues(List<AttrValue> attrValues) {
		this.attrValues = attrValues;
	}

	@Override
	public String toString() {
		return "Attr{" +
				"attrId=" + attrId +
				", shopId='" + shopId + '\'' +
				", name='" + name + '\'' +
				", desc='" + desc + '\'' +
				", searchType=" + searchType +
				", attrType=" + attrType +
				", attrValues=" + attrValues +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				'}';
	}
}
