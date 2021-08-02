package com.mall4j.cloud.multishop.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mall4j.cloud.api.product.vo.SpuVO;
import com.mall4j.cloud.common.serializer.ImgJsonSerializer;
import com.mall4j.cloud.common.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

/**
 * 轮播图VO
 *
 * @author YXF
 * @date 2020-11-24 16:38:32
 */
public class IndexImgVO extends BaseVO{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private Long imgId;

    @ApiModelProperty("店铺ID")
    private Long shopId;

    @ApiModelProperty("图片")
	@JsonSerialize(using = ImgJsonSerializer.class)
    private String imgUrl;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("顺序")
    private Integer seq;

    @ApiModelProperty("关联商品id")
    private Long spuId;

    @ApiModelProperty("图片类型 0:小程序 1:pc")
    private Integer imgType;

    @ApiModelProperty("spu信息")
	private SpuVO spu;

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

	public SpuVO getSpu() {
		return spu;
	}

	public void setSpu(SpuVO spu) {
		this.spu = spu;
	}

	@Override
	public String toString() {
		return "IndexImgVO{" +
				"imgId=" + imgId +
				",shopId=" + shopId +
				",imgUrl=" + imgUrl +
				",status=" + status +
				",seq=" + seq +
				",spuId=" + spuId +
				",imgType=" + imgType +
				",spu=" + spu +
				",createTime=" + createTime +
				",updateTime=" + updateTime +
				'}';
	}
}
