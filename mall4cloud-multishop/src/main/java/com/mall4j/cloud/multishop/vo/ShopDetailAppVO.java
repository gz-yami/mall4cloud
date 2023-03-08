package com.mall4j.cloud.multishop.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mall4j.cloud.api.vo.search.SpuSearchVO;
import com.mall4j.cloud.common.serializer.ImgJsonSerializer;
import com.mall4j.cloud.common.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * 店铺详情VO
 *
 * @author FrozenWatermelon
 * @date 2020-12-05 15:50:25
 */
public class ShopDetailAppVO extends BaseVO{
    private static final long serialVersionUID = 1L;

    @Schema(description = "店铺id" )
    private Long shopId;

    @Schema(description = "店铺类型1自营店 2普通店" )
    private Integer type;

    @Schema(description = "店铺名称" )
    private String shopName;

    @Schema(description = "店铺简介" )
    private String intro;

    @Schema(description = "店铺logo(可修改)" )
	@JsonSerialize(using = ImgJsonSerializer.class)
    private String shopLogo;

    @Schema(description = "店铺状态(-1:未开通 0: 停业中 1:营业中)" )
    private Integer shopStatus;

    @Schema(description = "营业执照" )
	@JsonSerialize(using = ImgJsonSerializer.class)
    private String businessLicense;

    @Schema(description = "身份证正面" )
	@JsonSerialize(using = ImgJsonSerializer.class)
    private String identityCardFront;

    @Schema(description = "身份证反面" )
	@JsonSerialize(using = ImgJsonSerializer.class)
    private String identityCardLater;

    @Schema(description = "商品列表" )
    private List<SpuSearchVO> spuList;

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getShopLogo() {
		return shopLogo;
	}

	public void setShopLogo(String shopLogo) {
		this.shopLogo = shopLogo;
	}

	public Integer getShopStatus() {
		return shopStatus;
	}

	public void setShopStatus(Integer shopStatus) {
		this.shopStatus = shopStatus;
	}

	public String getBusinessLicense() {
		return businessLicense;
	}

	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}

	public String getIdentityCardFront() {
		return identityCardFront;
	}

	public void setIdentityCardFront(String identityCardFront) {
		this.identityCardFront = identityCardFront;
	}

	public String getIdentityCardLater() {
		return identityCardLater;
	}

	public void setIdentityCardLater(String identityCardLater) {
		this.identityCardLater = identityCardLater;
	}

	public List<SpuSearchVO> getSpuList() {
		return spuList;
	}

	public void setSpuList(List<SpuSearchVO> spuList) {
		this.spuList = spuList;
	}

    @Override
    public String toString() {
        return "ShopDetailAppVO{" +
                "shopId=" + shopId +
                ", type=" + type +
                ", shopName='" + shopName + '\'' +
                ", intro='" + intro + '\'' +
                ", shopLogo='" + shopLogo + '\'' +
                ", shopStatus=" + shopStatus +
                ", businessLicense='" + businessLicense + '\'' +
                ", identityCardFront='" + identityCardFront + '\'' +
                ", identityCardLater='" + identityCardLater + '\'' +
                ", spuList=" + spuList +
                '}';
    }
}
