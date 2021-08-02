package com.mall4j.cloud.rbac.model;

import java.io.Serializable;

import com.mall4j.cloud.common.model.BaseModel;
/**
 * 菜单管理
 *
 * @author FrozenWatermelon
 * @date 2020-09-15 16:36:50
 */
public class Menu extends BaseModel implements Serializable{
    private static final long serialVersionUID = 1L;

    /**
     * 菜单id
     */
    private Long menuId;

    /**
     * 父菜单ID，一级菜单为0
     */
    private Long parentId;

    /**
     * 业务类型 0平台菜单 1 店铺菜单
     */
    private Integer bizType;

    /**
     * 权限，需要有哪个权限才能访问该菜单
     */
    private String permission;

    /**
     * 路径 就像uri
     */
    private String path;

    /**
     * 组件如：1.'Layout' 为布局，不会跳页面 2.'components-demo/tinymce' 跳转到该页面
     */
    private String component;

    /**
     * 当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
     */
    private String redirect;

    /**
     * 一直显示根路由
     */
    private Integer alwaysShow;

    /**
     * 当设置 true 的时候该路由不会在侧边栏出现 如401，login等页面，或者如一些编辑页面/edit/1
     */
    private Integer hidden;

    /**
     * 设定路由的名字，一定要填写不然使用<keep-alive>时会出现各种问题
     */
    private String name;

    /**
     * 设置该路由在侧边栏和面包屑中展示的名字
     */
    private String title;

    /**
     * 设置该路由的图标，支持 svg-class，也支持 el-icon-x element-ui 的 icon
     */
    private String icon;

    /**
     * 如果设置为true，则不会被 <keep-alive> 缓存(默认 false)
     */
    private Integer noCache;

    /**
     * 如果设置为false，则不会在breadcrumb面包屑中显示(默认 true)
     */
    private Integer breadcrumb;

    /**
     * 若果设置为true，它则会固定在tags-view中(默认 false)
     */
    private Integer affix;

    /**
     * 当路由设置了该属性，则会高亮相对应的侧边栏。
     */
    private String activeMenu;

    /**
     * 排序，越小越靠前
     */
    private Integer seq;

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getBizType() {
		return bizType;
	}

	public void setBizType(Integer bizType) {
		this.bizType = bizType;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
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

	public Integer getAlwaysShow() {
		return alwaysShow;
	}

	public void setAlwaysShow(Integer alwaysShow) {
		this.alwaysShow = alwaysShow;
	}

	public Integer getHidden() {
		return hidden;
	}

	public void setHidden(Integer hidden) {
		this.hidden = hidden;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	public Integer getNoCache() {
		return noCache;
	}

	public void setNoCache(Integer noCache) {
		this.noCache = noCache;
	}

	public Integer getBreadcrumb() {
		return breadcrumb;
	}

	public void setBreadcrumb(Integer breadcrumb) {
		this.breadcrumb = breadcrumb;
	}

	public Integer getAffix() {
		return affix;
	}

	public void setAffix(Integer affix) {
		this.affix = affix;
	}

	public String getActiveMenu() {
		return activeMenu;
	}

	public void setActiveMenu(String activeMenu) {
		this.activeMenu = activeMenu;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	@Override
	public String toString() {
		return "MenuVO{" +
				"menuId=" + menuId +
				",createTime=" + createTime +
				",updateTime=" + updateTime +
				",parentId=" + parentId +
				",bizType=" + bizType +
				",permission=" + permission +
				",path=" + path +
				",component=" + component +
				",redirect=" + redirect +
				",alwaysShow=" + alwaysShow +
				",hidden=" + hidden +
				",name=" + name +
				",title=" + title +
				",icon=" + icon +
				",noCache=" + noCache +
				",breadcrumb=" + breadcrumb +
				",affix=" + affix +
				",activeMenu=" + activeMenu +
				",seq=" + seq +
				'}';
	}
}
