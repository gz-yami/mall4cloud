package com.mall4j.cloud.api.vo.search;

import io.swagger.annotations.ApiModelProperty;

/**
 * 分类信息VO
 *
 * @author YXF
 * @date 2020-1-05 15:37:24
 */
public class CategorySearchVO{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("分类id")
    private Long categoryId;

    @ApiModelProperty("分类名称")
    private String name;

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "CategorySearchVO{" +
				"categoryId=" + categoryId +
				", name='" + name + '\'' +
				'}';
	}
}
