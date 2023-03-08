package com.mall4j.cloud.rbac.vo;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author FrozenWatermelon
 * @date 2020/8/6
 */
public class RouteVO {

	@Schema(description = "id" )
	private Long id;

	@Schema(description = "parentId" )
	private Long parentId;

	/**
	 * 就像uri
	 */
	@Schema(description = "路径: 就像uri" )
	private String path;

	/**
	 * 组件： 'layout/Layout' 为布局，不会跳页面 'views/components-demo/tinymce' 跳转到该页面
	 */
	@Schema(description = "组件如：1.'Layout' 为布局，不会跳页面 2.'components-demo/tinymce' 跳转到该页面" )
	private String component;

	/**
	 * 当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
	 */
	@Schema(description = "当设置 noRedirect 的时候该路由在面包屑导航中不可被点击" )
	private String redirect;

	/**
	 * 一直显示根路由
	 */
	@Schema(description = "一直显示根路由" )
	private Boolean alwaysShow;

	/**
	 * 当设置 true 的时候该路由不会在侧边栏出现 如401，login等页面，或者如一些编辑页面/edit/1
	 */
	@Schema(description = "当设置 true 的时候该路由不会在侧边栏出现 如401，login等页面，或者如一些编辑页面/edit/1" )
	private Boolean hidden;

	/**
	 * 设定路由的名字，一定要填写不然使用<keep-alive>时会出现各种问题
	 */
	@Schema(description = "设定路由的名字，一定要填写不然使用<keep-alive>时会出现各种问题" )
	private String name;

	/**
	 * 设定路由的名字，一定要填写不然使用<keep-alive>时会出现各种问题
	 */
	@Schema(description = "排序" )
	private Integer seq;

	/**
	 * 路由的源信息
	 */
	@Schema(description = "路由的源信息" )
	private RouteMetaVO meta;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}

	public Boolean getAlwaysShow() {
		return alwaysShow;
	}

	public void setAlwaysShow(Boolean alwaysShow) {
		this.alwaysShow = alwaysShow;
	}

	public Boolean getHidden() {
		return hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RouteMetaVO getMeta() {
		return meta;
	}

	public void setMeta(RouteMetaVO meta) {
		this.meta = meta;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	@Override
	public String toString() {
		return "RouteVO{" +
				"id=" + id +
				", parentId=" + parentId +
				", path='" + path + '\'' +
				", component='" + component + '\'' +
				", redirect='" + redirect + '\'' +
				", alwaysShow=" + alwaysShow +
				", hidden=" + hidden +
				", name='" + name + '\'' +
				", seq=" + seq +
				", meta=" + meta +
				'}';
	}

}
