package com.mall4j.cloud.multishop.model;

import java.io.Serializable;
import com.mall4j.cloud.common.model.BaseModel;
/**
 * 轮播图
 *
 * @author YXF
 * @date 2020-11-24 16:38:32
 */
public class IndexImg extends BaseModel implements Serializable{
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long imgId;

    /**
     * 店铺ID
     */
    private Long shopId;

    /**
     * 图片
     */
    private String imgUrl;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 顺序
     */
    private Integer seq;

    /**
     * 关联商品id
     */
    private Long spuId;

    /**
     * 图片类型 0:小程序 1:pc
     */
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
		return "IndexImg{" +
				"imgId=" + imgId +
				",shopId=" + shopId +
				",imgUrl=" + imgUrl +
				",status=" + status +
				",seq=" + seq +
				",spuId=" + spuId +
				",imgType=" + imgType +
				",createTime=" + createTime +
				",updateTime=" + updateTime +
				'}';
	}
}
