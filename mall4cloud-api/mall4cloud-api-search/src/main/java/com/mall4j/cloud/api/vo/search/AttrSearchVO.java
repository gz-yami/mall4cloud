package com.mall4j.cloud.api.vo.search;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author FrozenWatermelon
 * @date 2020/11/17
 */
public class AttrSearchVO {

    @ApiModelProperty(value = "规格id")
    private Long attrId;

    @ApiModelProperty(value = "规格名")
    private String attrName;

    @ApiModelProperty(value = "规格值列表")
    private List<AttrValueSearchVO> attrValues;

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

    public List<AttrValueSearchVO> getAttrValues() {
        return attrValues;
    }

    public void setAttrValues(List<AttrValueSearchVO> attrValues) {
        this.attrValues = attrValues;
    }

    @Override
    public String toString() {
        return "AttrVO{" +
                "attrId=" + attrId +
                ", attrName='" + attrName + '\'' +
                ", attrValues=" + attrValues +
                '}';
    }
}
