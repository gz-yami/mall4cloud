package com.mall4j.cloud.api.product.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mall4j.cloud.common.serializer.ImgJsonSerializer;
import com.mall4j.cloud.common.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * 品牌信息VO
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
public class BrandVO extends BaseVO{
    private static final long serialVersionUID = 1L;

    @Schema(description = "brand_id" )
    private Long brandId;

    @Schema(description = "品牌名称" )
    private String name;

    @Schema(description = "品牌描述" )
    private String desc;

    @Schema(description = "品牌logo图片" )
	@JsonSerialize(using = ImgJsonSerializer.class)
    private String imgUrl;

    @Schema(description = "检索首字母" )
    private String firstLetter;

    @Schema(description = "排序" )
    private Integer seq;

    @Schema(description = "状态 1:enable, 0:disable, -1:deleted" )
    private Integer status;

	@Schema(description = "分类" )
	private List<CategoryVO> categories;

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
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

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getFirstLetter() {
		return firstLetter;
	}

	public void setFirstLetter(String firstLetter) {
		this.firstLetter = firstLetter;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<CategoryVO> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryVO> categories) {
		this.categories = categories;
	}

	@Override
	public String toString() {
		return "BrandVO{" +
				"brandId=" + brandId +
				", name='" + name + '\'' +
				", desc='" + desc + '\'' +
				", imgUrl='" + imgUrl + '\'' +
				", firstLetter='" + firstLetter + '\'' +
				", seq=" + seq +
				", status=" + status +
				", categories=" + categories +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				'}';
	}
}
