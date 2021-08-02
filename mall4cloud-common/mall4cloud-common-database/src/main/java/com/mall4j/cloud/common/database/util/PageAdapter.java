package com.mall4j.cloud.common.database.util;

import com.mall4j.cloud.common.database.dto.PageDTO;


/**
 * 分页适配
 * @author FrozenWatermelon
 */
public class PageAdapter {

    private int begin;

    private int size;

    public PageAdapter(PageDTO page) {
        this.begin = getStart(page.getPageNum() - 1, page.getPageSize());
        this.size = page.getPageSize();
    }

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public static int getStart(int pageNo, int pageSize) {
        if (pageNo < 0) {
            pageNo = 0;
        }

        if (pageSize < 1) {
            pageSize = 0;
        }

        return pageNo * pageSize;
    }

}
