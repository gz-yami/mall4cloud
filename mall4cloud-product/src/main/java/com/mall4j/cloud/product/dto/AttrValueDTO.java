package com.mall4j.cloud.product.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * 属性值信息DTO
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
public class AttrValueDTO{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("属性id")
    private Long attrValueId;

    @ApiModelProperty("属性ID")
    private Long attrId;

    @ApiModelProperty("属性值")
    private String value;

	public Long getAttrValueId() {
		return attrValueId;
	}

	public void setAttrValueId(Long attrValueId) {
		this.attrValueId = attrValueId;
	}

	public Long getAttrId() {
		return attrId;
	}

	public void setAttrId(Long attrId) {
		this.attrId = attrId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "AttrValueDTO{" +
				"attrValueId=" + attrValueId +
				",attrId=" + attrId +
				",value=" + value +
				'}';
	}
}
