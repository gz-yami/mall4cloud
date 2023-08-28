/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */
package com.mall4j.cloud.common.util;


import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import java.util.List;

/**
 * @author lanhai
 */
public class BeanUtil {
    public static <S, D> List<D> mapAsList(final Iterable<S> sourceObject, Class<D> clazz) {
        return JSONArray.parseArray(JSONArray.toJSONString(sourceObject), clazz);
    }
    public static <S, D> D map(final S sourceObject, Class<D> clazz) {
        return JSONObject.parseObject(JSONObject.toJSONString(sourceObject), clazz);
    }
}
