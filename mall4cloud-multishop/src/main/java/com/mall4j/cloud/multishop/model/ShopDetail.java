package com.mall4j.cloud.multishop.model;

import java.io.Serializable;

import com.mall4j.cloud.common.model.BaseModel;
/**
 * 店铺详情
 *
 * @author FrozenWatermelon
 * @date 2020-12-05 15:50:25
 */
public class ShopDetail extends BaseModel implements Serializable{
    private static final long serialVersionUID = 1L;

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 店铺类型1自营店 2普通店
     */
    private Integer type;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 店铺简介
     */
    private String intro;

    /**
     * 店铺logo(可修改)
     */
    private String shopLogo;

    /**
     * 店铺状态(-1:已删除 0: 停业中 1:营业中)
     */
    private Integer shopStatus;

    /**
     * 营业执照
     */
    private String businessLicense;

    /**
     * 身份证正面
     */
    private String identityCardFront;

    /**
     * 身份证反面
     */
    private String identityCardLater;

	/**
	 * 移动端背景图
	 */
	private String mobileBackgroundPic;

	/**
	 * pc背景图
	 */
	private String pcBackgroundPic;

	public String getMobileBackgroundPic() {
		return mobileBackgroundPic;
	}

	public void setMobileBackgroundPic(String mobileBackgroundPic) {
		this.mobileBackgroundPic = mobileBackgroundPic;
	}

	public String getPcBackgroundPic() {
		return pcBackgroundPic;
	}

	public void setPcBackgroundPic(String pcBackgroundPic) {
		this.pcBackgroundPic = pcBackgroundPic;
	}

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

	@Override
	public String toString() {
		return "ShopDetail{" +
				"shopId=" + shopId +
				", type=" + type +
				", shopName='" + shopName + '\'' +
				", intro='" + intro + '\'' +
				", shopLogo='" + shopLogo + '\'' +
				", shopStatus=" + shopStatus +
				", businessLicense='" + businessLicense + '\'' +
				", identityCardFront='" + identityCardFront + '\'' +
				", identityCardLater='" + identityCardLater + '\'' +
				", mobileBackgroundPic='" + mobileBackgroundPic + '\'' +
				", pcBackgroundPic='" + pcBackgroundPic + '\'' +
				'}';
	}
}
