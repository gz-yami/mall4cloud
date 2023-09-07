package com.mall4j.cloud.biz.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO
 *
 * @author YXF
 * @date 2020-12-04 16:15:02
 */
public class AttachFileGroupDTO{
    private static final long serialVersionUID = 1L;

    
    private Long attachFileGroupId;

    @Schema(description = "店铺id" )
    private Long shopId;

    @Schema(description = "分组名称" )
    private String name;

	public Long getAttachFileGroupId() {
		return attachFileGroupId;
	}

	public void setAttachFileGroupId(Long attachFileGroupId) {
		this.attachFileGroupId = attachFileGroupId;
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

	@Override
	public String toString() {
		return "AttachFileGroupDTO{" +
				"attachFileGroupId=" + attachFileGroupId +
				",shopId=" + shopId +
				",name=" + name +
				'}';
	}
}
