package com.mall4j.cloud.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * @author FrozenWatermelon
 * @date 2020/11/16
 */
public class EsPageVO<T> {

    @Schema(description = "总页数" )
    private Integer pages;

    @Schema(description = "总条目数" )
    private Long total;

    @Schema(description = "结果集" )
    private List<T> list;

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "EsPageVO{" +
                ", pages=" + pages +
                ", total=" + total +
                ", list=" + list +
                '}';
    }
}
