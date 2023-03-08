package com.mall4j.cloud.order.vo;

import com.mall4j.cloud.common.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 用户订单配送地址VO
 *
 * @author FrozenWatermelon
 * @date 2020-12-05 14:13:50
 */
public class OrderAddrVO extends BaseVO{
    private static final long serialVersionUID = 1L;

    @Schema(description = "ID" )
    private Long orderAddrId;

    @Schema(description = "用户ID" )
    private Long userId;

    @Schema(description = "收货人" )
    private String consignee;

    @Schema(description = "省ID" )
    private Long provinceId;

    @Schema(description = "省" )
    private String province;

    @Schema(description = "城市ID" )
    private Long cityId;

    @Schema(description = "城市" )
    private String city;

    @Schema(description = "区域ID" )
    private Long areaId;

    @Schema(description = "区" )
    private String area;

    @Schema(description = "地址" )
    private String addr;

    @Schema(description = "邮编" )
    private String postCode;

    @Schema(description = "手机" )
    private String mobile;

	public Long getOrderAddrId() {
		return orderAddrId;
	}

	public void setOrderAddrId(Long orderAddrId) {
		this.orderAddrId = orderAddrId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public Long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Override
	public String toString() {
		return "OrderAddrVO{" +
				"orderAddrId=" + orderAddrId +
				",createTime=" + createTime +
				",updateTime=" + updateTime +
				",userId=" + userId +
				",consignee=" + consignee +
				",provinceId=" + provinceId +
				",province=" + province +
				",cityId=" + cityId +
				",city=" + city +
				",areaId=" + areaId +
				",area=" + area +
				",addr=" + addr +
				",postCode=" + postCode +
				",mobile=" + mobile +
				'}';
	}
}
