package com.mall4j.cloud.multishop.model;

import java.io.Serializable;

import com.mall4j.cloud.common.model.BaseModel;
/**
 * 热搜
 *
 * @author YXF
 * @date 2021-01-27 09:10:00
 */
public class HotSearch extends BaseModel implements Serializable{
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long hotSearchId;

    /**
     * 店铺ID 0为全局热搜
     */
    private Long shopId;

    /**
     * 内容
     */
    private String content;

    /**
     * 顺序
     */
    private Integer seq;

    /**
     * 状态 0下线 1上线
     */
    private Integer status;

    /**
     * 热搜标题
     */
    private String title;

	public Long getHotSearchId() {
		return hotSearchId;
	}

	public void setHotSearchId(Long hotSearchId) {
		this.hotSearchId = hotSearchId;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "HotSearch{" +
				"hotSearchId=" + hotSearchId +
				", shopId=" + shopId +
				", content='" + content + '\'' +
				", seq=" + seq +
				", status=" + status +
				", title='" + title + '\'' +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				'}';
	}
}
