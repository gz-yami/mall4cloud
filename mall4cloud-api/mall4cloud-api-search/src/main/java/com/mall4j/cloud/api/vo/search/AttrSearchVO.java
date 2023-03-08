package com.mall4j.cloud.api.vo.search;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * @author FrozenWatermelon
 * @date 2020/11/17
 */
public class AttrSearchVO {

    @Schema(description = "规格id" )
    private Long attrId;

    @Schema(description = "规格名" )
    private String attrName;

    @Schema(description = "规格值列表" )
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
