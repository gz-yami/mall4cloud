package com.mall4j.cloud.product.model;

import com.mall4j.cloud.common.model.BaseModel;

import java.io.Serializable;

/**
 * 商品sku销售属性关联信息
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
public class SpuSkuAttrValue extends BaseModel implements Serializable{
    private static final long serialVersionUID = 1L;

    /**
     * 商品sku销售属性关联信息id
     */
    private Long spuSkuAttrId;

    /**
     * SPU ID
     */
    private Long spuId;

    /**
     * SKU ID
     */
    private Long skuId;

    /**
     * 销售属性ID
     */
    private Long attrId;

    /**
     * 销售属性名称
     */
    private String attrName;

    /**
     * 销售属性值ID
     */
    private Long attrValueId;

    /**
     * 销售属性值
     */
    private String attrValueName;

    /**
     * 状态 1:enable, 0:disable, -1:deleted
     */
    private Integer status;

	public Long getSpuSkuAttrId() {
		return spuSkuAttrId;
	}

	public void setSpuSkuAttrId(Long spuSkuAttrId) {
		this.spuSkuAttrId = spuSkuAttrId;
	}

	public Long getSpuId() {
		return spuId;
	}

	public void setSpuId(Long spuId) {
		this.spuId = spuId;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "SpuSkuAttrValue{" +
				"spuSkuAttrId=" + spuSkuAttrId +
				",createTime=" + createTime +
				",updateTime=" + updateTime +
				",spuId=" + spuId +
				",skuId=" + skuId +
				",attrId=" + attrId +
				",attrName=" + attrName +
				",attrValueId=" + attrValueId +
				",attrValueName=" + attrValueName +
				",status=" + status +
				'}';
	}
}
