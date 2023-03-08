package com.mall4j.cloud.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;

/**
 * 用户地址DTO
 *
 * @author FrozenWatermelon
 * @date 2020-12-07 15:50:02
 */
public class UserAddrDTO {
    private static final long serialVersionUID = 1L;

    @Schema(description = "ID" )
    private Long addrId;

    @Schema(description = "手机" )
    private String mobile;

    @Schema(description = "是否默认地址 1是" )
    private Integer isDefault;

    @Schema(description = "收货人" )
	@Length(min = 2, max = 20, message = "收货人姓名需要在2到20个字符之间")
    private String consignee;

    @Schema(description = "省ID" )
    private Long provinceId;

    @Schema(description = "省" )
    private String province;

    @Schema(description = "城市ID" )
    private Long cityId;

    @Schema(description = "城市" )
    private String city;

    @Schema(description = "区ID" )
    private Long areaId;

    @Schema(description = "区" )
    private String area;

    @Schema(description = "邮编" )
    private String postCode;

    @Schema(description = "地址" )
    private String addr;

    @Schema(description = "经度" )
    private Double lng;

    @Schema(description = "纬度" )
    private Double lat;

	public Long getAddrId() {
		return addrId;
	}

	public void setAddrId(Long addrId) {
		this.addrId = addrId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
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

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	@Override
	public String toString() {
		return "UserAddrDTO{" +
				"addrId=" + addrId +
				",mobile=" + mobile +
				",isDefault=" + isDefault +
				",consignee=" + consignee +
				",provinceId=" + provinceId +
				",province=" + province +
				",cityId=" + cityId +
				",city=" + city +
				",areaId=" + areaId +
				",area=" + area +
				",postCode=" + postCode +
				",addr=" + addr +
				",lng=" + lng +
				",lat=" + lat +
				'}';
	}
}
