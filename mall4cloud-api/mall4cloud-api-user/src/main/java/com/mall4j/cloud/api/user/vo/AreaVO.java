package com.mall4j.cloud.api.user.vo;

import com.mall4j.cloud.common.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 省市区地区信息VO
 *
 * @author YXF
 * @date 2020-11-25 15:16:14
 */
public class AreaVO extends BaseVO{
    private static final long serialVersionUID = 1L;

	@ApiModelProperty()
	private Long areaId;

	@ApiModelProperty("地址")
	private String areaName;

	@ApiModelProperty("上级地址")
	private Long parentId;

	@ApiModelProperty("等级（从1开始）")
	private Integer level;

	private Integer check;

	/**
	 * 下级地址集合
	 */
	private List<AreaVO> areas;

	/**
	 * 下级地址的areaId
	 */
	private List<Long> areaIds;

	public Long getAreaId() {
		return areaId;
	}

	public Integer getCheck() {
		return check;
	}

	public void setCheck(Integer check) {
		this.check = check;
	}

	public List<AreaVO> getAreas() {
		return areas;
	}

	public void setAreas(List<AreaVO> areas) {
		this.areas = areas;
	}

	public List<Long> getAreaIds() {
		return areaIds;
	}

	public void setAreaIds(List<Long> areaIds) {
		this.areaIds = areaIds;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "AreaDTO{" +
				"areaId=" + areaId +
				", areaName='" + areaName + '\'' +
				", parentId=" + parentId +
				", level=" + level +
				", check=" + check +
				", areas=" + areas +
				", areaIds=" + areaIds +
				'}';
	}
}
