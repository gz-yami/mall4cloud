package com.mall4j.cloud.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

/**
 * 分类信息DTO
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
public class CategoryDTO{
    private static final long serialVersionUID = 1L;

    @Schema(description = "分类id" )
    private Long categoryId;

    @Schema(description = "店铺id" )
    private Long shopId;

	@NotNull(message = "请选择上级分类")
    @Schema(description = "父ID" )
    private Long parentId;

	@NotNull(message = "分类名称不能为空")
    @Schema(description = "分类名称" )
    private String name;

    @Schema(description = "分类描述" )
    private String desc;

    @Schema(description = "分类地址{parent_id}-{child_id},..." )
    private String path;

	@NotNull(message = "状态不能为空")
    @Schema(description = "状态 1:enable, 0:disable, -1:deleted" )
    private Integer status;

    @Schema(description = "分类图标" )
    private String icon;

    @Schema(description = "分类的显示图片" )
    private String imgUrl;

    @Schema(description = "分类层级 从0开始" )
    private Integer level;

	@Schema(description = "排序" )
	private Integer seq;

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	@Override
	public String toString() {
		return "CategoryDTO{" +
				"categoryId=" + categoryId +
				", shopId=" + shopId +
				", parentId=" + parentId +
				", name='" + name + '\'' +
				", desc='" + desc + '\'' +
				", path='" + path + '\'' +
				", status=" + status +
				", icon='" + icon + '\'' +
				", imgUrl='" + imgUrl + '\'' +
				", level=" + level +
				", seq=" + seq +
				'}';
	}
}
