package com.mall4j.cloud.multishop.dto;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;

/**
 * 轮播图DTO
 *
 * @author YXF
 * @date 2020-11-24 16:38:32
 */
public class IndexImgDTO{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private Long imgId;

    @ApiModelProperty("店铺ID")
    private Long shopId;

    @NotNull(message = "图片不能为空")
    @ApiModelProperty("图片")
    private String imgUrl;

    @ApiModelProperty("状态")
    private Integer status;

    @NotNull(message = "序号不能为空")
    @ApiModelProperty("顺序")
    private Integer seq;

    @ApiModelProperty("关联商品id")
    private Long spuId;

	@NotNull(message = "图片类型不能为空")
    @ApiModelProperty("图片类型 0:小程序 1:pc")
    private Integer imgType;

	public Long getImgId() {
		return imgId;
	}

	public void setImgId(Long imgId) {
		this.imgId = imgId;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Long getSpuId() {
		return spuId;
	}

	public void setSpuId(Long spuId) {
		this.spuId = spuId;
	}

	public Integer getImgType() {
		return imgType;
	}

	public void setImgType(Integer imgType) {
		this.imgType = imgType;
	}

	@Override
	public String toString() {
		return "IndexImgDTO{" +
				"imgId=" + imgId +
				",shopId=" + shopId +
				",imgUrl=" + imgUrl +
				",status=" + status +
				",seq=" + seq +
				",spuId=" + spuId +
				",imgType=" + imgType +
				'}';
	}
}
