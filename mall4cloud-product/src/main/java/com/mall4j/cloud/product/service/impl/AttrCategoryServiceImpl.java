package com.mall4j.cloud.product.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.mall4j.cloud.api.product.vo.CategoryVO;
import com.mall4j.cloud.product.mapper.AttrCategoryMapper;
import com.mall4j.cloud.product.service.AttrCategoryService;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 属性与属性分组关联信息
 *
 * @author YXF
 * @date 2020-11-23 16:20:01
 */
@Service
public class AttrCategoryServiceImpl implements AttrCategoryService {

    @Autowired
    private AttrCategoryMapper attrCategoryMapper;

    @Override
    public void save(Long attrId, List<Long> categoryIds) {
        attrCategoryMapper.saveBatch(attrId, categoryIds);
    }

    @Override
    public List<Long> update(Long attrId, List<Long> categoryIds) {
        if (CollUtil.isEmpty(categoryIds)) {
            return new ArrayList<>();
        }
        List<Long> dbCategoryIds = attrCategoryMapper.getCategoryIdsByAttrId(attrId);
        List<Long> addList = new ArrayList<>(categoryIds.size());
        addList.addAll(categoryIds);
        addList.removeAll(dbCategoryIds);
        if (CollUtil.isNotEmpty(addList)) {
            attrCategoryMapper.saveBatch(attrId, addList);
        }
        dbCategoryIds.removeAll(categoryIds);
        if (CollUtil.isNotEmpty(dbCategoryIds)) {
            attrCategoryMapper.deleteBatch(attrId, dbCategoryIds);
        }
        return dbCategoryIds;
    }

    @Override
    public List<CategoryVO> listByAttrId(Long attrId) {
        return attrCategoryMapper.listByAttrId(attrId);
    }
}
