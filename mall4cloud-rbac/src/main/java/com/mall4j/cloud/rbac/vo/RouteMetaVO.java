package com.mall4j.cloud.rbac.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

/**
 * @author FrozenWatermelon
 * @date 2020/8/7
 */
public class RouteMetaVO {

	/**
	 * 设置该路由在侧边栏和面包屑中展示的名字
	 */
	@Schema(description = "设置该路由在侧边栏和面包屑中展示的名字" )
	private String title;

	/**
	 * 设置该路由的图标，支持 svg-class，也支持 el-icon-x element-ui 的 icon
	 */
	@Schema(description = "设置该路由的图标，支持 svg-class，也支持 el-icon-x element-ui 的 icon" )
	private String icon;

	/**
	 * 如果设置为true，则不会被 <keep-alive> 缓存(默认 false)
	 */
	@Schema(description = "如果设置为true，则不会被 <keep-alive> 缓存(默认 false)" )
	private Boolean noCache;

	/**
	 * 如果设置为false，则不会在breadcrumb面包屑中显示(默认 true)
	 */
	@Schema(description = "如果设置为false，则不会在breadcrumb面包屑中显示(默认 true)" )
	private Boolean breadcrumb;

	/**
	 * 若果设置为true，它则会固定在tags-view中(默认 false)
	 */
	@Schema(description = "若果设置为true，它则会固定在tags-view中(默认 false)" )
	private Boolean affix;

	/**
	 * 当路由设置了该属性，则会高亮相对应的侧边栏。 这在某些场景非常有用，比如：一个文章的列表页路由为：/article/list
	 * 点击文章进入文章详情页，这时候路由为/article/1，但你想在侧边栏高亮文章列表的路由，就可以进行如下设置
	 */
	@Schema(description = "当路由设置了该属性，则会高亮相对应的侧边栏。" )
	private String activeMenu;

	@Schema(description = "需要什么权限才能访问该菜单" )
	private List<String> roles;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Boolean getNoCache() {
		return noCache;
	}

	public void setNoCache(Boolean noCache) {
		this.noCache = noCache;
	}

	public Boolean getBreadcrumb() {
		return breadcrumb;
	}

	public void setBreadcrumb(Boolean breadcrumb) {
		this.breadcrumb = breadcrumb;
	}

	public Boolean getAffix() {
		return affix;
	}

	public void setAffix(Boolean affix) {
		this.affix = affix;
	}

	public String getActiveMenu() {
		return activeMenu;
	}

	public void setActiveMenu(String activeMenu) {
		this.activeMenu = activeMenu;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "RouteMetaVO{" + "title='" + title + '\'' + ", icon='" + icon + '\'' + ", noCache=" + noCache
				+ ", breadcrumb=" + breadcrumb + ", affix=" + affix + ", activeMenu='" + activeMenu + '\'' + ", roles="
				+ roles + '}';
	}

}
