package com.mall4j.cloud.api.product.vo;

import com.mall4j.cloud.common.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 商品sku销售属性关联信息VO
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
public class SpuSkuAttrValueVO extends BaseVO {
	private static final long serialVersionUID = 1L;

	@Schema(description = "商品sku销售属性关联信息id" )
	private Integer spuSkuAttrId;

	@Schema(description = "SPU ID" )
	private Long spuId;

	@Schema(description = "SKU ID" )
	private Long skuId;

	@Schema(description = "销售属性ID" )
	private Integer attrId;

	@Schema(description = "销售属性名称" )
	private String attrName;

	@Schema(description = "销售属性值ID" )
	private Integer attrValueId;

	@Schema(description = "销售属性值" )
	private String attrValueName;

	@Schema(description = "状态 1:enable, 0:disable, -1:deleted" )
	private Integer status;

	public Integer getSpuSkuAttrId() {
		return spuSkuAttrId;
	}

	public void setSpuSkuAttrId(Integer spuSkuAttrId) {
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

	public Integer getAttrId() {
		return attrId;
	}

	public void setAttrId(Integer attrId) {
		this.attrId = attrId;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public Integer getAttrValueId() {
		return attrValueId;
	}

	public void setAttrValueId(Integer attrValueId) {
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
		return "SpuSkuAttrValueVO{" +
				"spuSkuAttrId=" + spuSkuAttrId +
				", spuId=" + spuId +
				", skuId=" + skuId +
				", attrId=" + attrId +
				", attrName='" + attrName + '\'' +
				", attrValueId=" + attrValueId +
				", attrValueName='" + attrValueName + '\'' +
				", status=" + status +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				'}';
	}
}
