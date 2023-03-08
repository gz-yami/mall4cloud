package com.mall4j.cloud.api.product.vo;

import com.mall4j.cloud.common.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 商品规格属性关联信息VO
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
public class SpuAttrValueVO extends BaseVO {
    private static final long serialVersionUID = 1L;

    @Schema(description = "商品属性值关联信息id" )
    private Long spuAttrValueId;

    @Schema(description = "商品id" )
    private Long spuId;

    @Schema(description = "规格属性id" )
    private Long attrId;

    @Schema(description = "规格属性名称" )
    private String attrName;

    @Schema(description = "规格属性值id" )
    private Long attrValueId;

	@Schema(description = "规格属性值名称" )
	private String attrValueName;

	@Schema(description = "搜索类型 0:不需要，1:需要" )
	private Integer searchType;

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

	public Integer getSearchType() {
		return searchType;
	}

	public void setSearchType(Integer searchType) {
		this.searchType = searchType;
	}

	@Override
	public String toString() {
		return "SpuAttrValueVO{" +
				"spuAttrValueId=" + spuAttrValueId +
				", spuId=" + spuId +
				", attrId=" + attrId +
				", attrName='" + attrName + '\'' +
				", attrValueId=" + attrValueId +
				", attrValueName='" + attrValueName + '\'' +
				", searchType=" + searchType +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				'}';
	}
}
