package com.mall4j.cloud.api.product.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mall4j.cloud.common.serializer.ImgJsonSerializer;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 分类信息VO
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
public class CategoryVO{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("分类id")
    private Long categoryId;

    @ApiModelProperty("店铺id")
    private Long shopId;

    @ApiModelProperty("父ID")
    private Long parentId;

    @ApiModelProperty("分类名称")
    private String name;

    @ApiModelProperty("分类描述")
    private String desc;

    @ApiModelProperty("分类地址{parent_id}-{child_id},...")
    private String path;

    @ApiModelProperty("状态 1:enable, 0:disable, -1:deleted")
    private Integer status;

	@JsonSerialize(using = ImgJsonSerializer.class)
    @ApiModelProperty("分类图标")
    private String icon;

	@JsonSerialize(using = ImgJsonSerializer.class)
    @ApiModelProperty("分类的显示图片")
    private String imgUrl;

    @ApiModelProperty("分类层级 从0开始")
    private Integer level;

	@ApiModelProperty("排序")
	private Integer seq;

	@ApiModelProperty("上级分类名称")
	private List<String> pathNames;

	@ApiModelProperty("子分类列表")
	private List<CategoryVO> categories;

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

	public List<String> getPathNames() {
		return pathNames;
	}

	public void setPathNames(List<String> pathNames) {
		this.pathNames = pathNames;
	}

	public List<CategoryVO> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryVO> categories) {
		this.categories = categories;
	}

	@Override
	public String toString() {
		return "CategoryVO{" +
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
				", pathNames=" + pathNames +
				", categories=" + categories +
				'}';
	}
}
