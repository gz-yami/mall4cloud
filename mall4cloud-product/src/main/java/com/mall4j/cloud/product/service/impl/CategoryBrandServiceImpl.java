package com.mall4j.cloud.product.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.mall4j.cloud.product.model.CategoryBrand;
import com.mall4j.cloud.product.mapper.CategoryBrandMapper;
import com.mall4j.cloud.product.service.CategoryBrandService;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 品牌分类关联信息
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
@Service
public class CategoryBrandServiceImpl implements CategoryBrandService {

    @Autowired
    private CategoryBrandMapper categoryBrandMapper;

    @Override
    public void deleteByBrandId(Long brandId) {
        categoryBrandMapper.deleteByBrandId(brandId);
    }

    @Override
    public void saveByCategoryIds(Long brandId, List<Long> categoryIds) {
        if (CollUtil.isEmpty(categoryIds)) {
            return;
        }
        List<CategoryBrand> categoryBrandList = new ArrayList<>();
        categoryIds.forEach(categoryId -> {
            CategoryBrand categoryBrand = new CategoryBrand();
            categoryBrand.setBrandId(brandId);
            categoryBrand.setCategoryId(categoryId);
            categoryBrandList.add(categoryBrand);
        });
        categoryBrandMapper.saveBatch(categoryBrandList);
    }

    @Override
    public void updateByCategoryIds(Long brandId, List<Long> categoryIds) {
        if (CollUtil.isEmpty(categoryIds)) {
            return;
        }
        List<Long> categoryIdDb = getCategoryIdBrandId(brandId);
        List<Long> addList = new ArrayList<>();
        categoryIds.forEach(categoryId -> {
            if (!categoryIdDb.contains(categoryId)) {
                addList.add(categoryId);
            }
        });
        if(CollUtil.isNotEmpty(addList)) {
            saveByCategoryIds(brandId, addList);
        }
        categoryIdDb.removeAll(categoryIds);
        if (CollUtil.isNotEmpty(categoryIdDb)) {
            categoryBrandMapper.deleteByBrandIdAndCategoryIds(brandId, categoryIdDb);
        }
    }

    @Override
    public List<Long> getCategoryIdBrandId(Long brandId) {
        return categoryBrandMapper.getCategoryIdsByBrandId(brandId);
    }
}
