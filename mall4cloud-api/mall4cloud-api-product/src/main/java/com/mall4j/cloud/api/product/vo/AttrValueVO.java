package com.mall4j.cloud.api.product.vo;

import com.mall4j.cloud.common.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 属性值信息VO
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
public class AttrValueVO extends BaseVO{
    private static final long serialVersionUID = 1L;

    @Schema(description = "属性id" )
    private Long attrValueId;

    @Schema(description = "属性ID" )
    private Long attrId;

    @Schema(description = "属性值" )
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
		return "AttrValueVO{" +
				"attrValueId=" + attrValueId +
				",createTime=" + createTime +
				",updateTime=" + updateTime +
				",attrId=" + attrId +
				",value=" + value +
				'}';
	}
}
