package com.mall4j.cloud.product.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.mall4j.cloud.common.cache.constant.CacheNames;
import com.mall4j.cloud.common.constant.Constant;
import com.mall4j.cloud.common.constant.StatusEnum;
import com.mall4j.cloud.common.exception.Mall4cloudException;
import com.mall4j.cloud.common.security.AuthUserContext;
import com.mall4j.cloud.api.product.constant.CategoryLevel;
import com.mall4j.cloud.product.dto.CategoryDTO;
import com.mall4j.cloud.product.model.Category;
import com.mall4j.cloud.product.mapper.CategoryMapper;
import com.mall4j.cloud.product.service.CategoryService;
import com.mall4j.cloud.api.product.vo.CategoryVO;
import com.mall4j.cloud.product.service.SpuService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 分类信息
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public CategoryVO getById(Long categoryId) {
        CategoryVO category = categoryMapper.getById(categoryId);
        if (Objects.isNull(category)) {
//            throw new mall4cloudException("分类不存在，请刷新后重试");
            return category;
        }
        List<CategoryVO> paths = new ArrayList<>();
        paths.add(category);
        getPathNames(paths);
        return category;
    }

    @Override
    public void save(Category category) {
        existCategoryName(category);
        category.setShopId(AuthUserContext.get().getTenantId());
        String path = "";
        if (!Objects.equals(CategoryLevel.First.value(), category.getLevel())) {
            String parentId = String.valueOf(category.getParentId());
            CategoryVO categoryDb = categoryMapper.getById(category.getParentId());
            if (StrUtil.isBlank(categoryDb.getPath())) {
                path = parentId;
            } else {
                path = categoryDb.getPath() + Constant.CATEGORY_INTERVAL + parentId;
            }
        }
        category.setPath(path);
        categoryMapper.save(category);
    }

    @Override
    public void update(Category category) {
        CategoryVO dbCategory = getById(category.getCategoryId());
        if (Objects.equals(dbCategory.getCategoryId(), category.getParentId())) {
            throw new Mall4cloudException("分类不能成为本身的上级分类");
        }
        existCategoryName(category);
        int updateCount = categoryMapper.update(category);
    }

    @Override
    public void deleteById(Long categoryId) {
        int count = categoryMapper.getCategoryUseNum(categoryId);
        if (count > 0) {
            throw new Mall4cloudException("该分类在使用中，不能进行删除操作");
        }
        categoryMapper.deleteById(categoryId);
    }

    @Override
    public List<CategoryVO> list(Long shopId) {
        return categoryMapper.list(shopId);
    }

    @Override
    public List<Long> listCategoryId(Long shopId, Long parentId) {
        return categoryMapper.listCategoryId(shopId, parentId);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = CacheNames.CATEGORY_LIST_BY_PARENT_ID_AND_SHOP_ID, key = "#shopId + ':' + #parentId"),
            @CacheEvict(cacheNames = CacheNames.CATEGORY_LIST_ALL_OF_SHOP, key = "#shopId"),
            @CacheEvict(cacheNames = CacheNames.CATEGORY_LIST, key = "#shopId + ':' + #parentId")
    })
    public void removeCategoryCache(Long shopId, Long parentId) {
    }

    @Override
    public void getPathNames(List<CategoryVO> categorys) {
        if (CollUtil.isEmpty(categorys)) {
            return;
        }
        // 获取分类的所有上级分类id集合
        Set<Long> paths = new HashSet<>();
        for (CategoryVO category : categorys) {
            if (Objects.isNull(category) || StrUtil.isBlank(category.getPath())) {
                continue;
            }
            String[] parentIds = category.getPath().split(Constant.CATEGORY_INTERVAL);
            for (String parentId : parentIds) {
                paths.add(Long.valueOf(parentId));
            }
        }
        if (CollUtil.isEmpty(paths)) {
            return;
        }

        // 获取所有上级分类id列表
        List<Category> listByCategoryIds = categoryMapper.getListByCategoryIds(paths);
        Map<Long, Category> categoryMap = listByCategoryIds.stream().collect(Collectors.toMap(Category::getCategoryId, c -> c));

        // 获取每个分类的上级分类名称集合
        for (CategoryVO category : categorys) {
            if (StrUtil.isBlank(category.getPath())) {
                continue;
            }
            String[] parentIdArray = category.getPath().split(Constant.CATEGORY_INTERVAL);
            category.setPathNames(new ArrayList<>());
            for (int i = 0; i < parentIdArray.length; i++) {
                String pathName = categoryMap.get(Long.valueOf(parentIdArray[i])).getName();
                category.getPathNames().add(pathName);
            }
        }
    }

    @Override
    public CategoryVO getPathNameByCategoryId(Long categoryId) {
        CategoryVO category = getById(categoryId);
        if (Objects.isNull(category)) {
            return null;
        }
        List<CategoryVO> categorys = new ArrayList<>(1);
        categorys.add(category);
        getPathNames(categorys);
        return category;
    }

    @Override
    @Cacheable(cacheNames = CacheNames.CATEGORY_LIST, key = "#shopId + ':' + #parentId")
    public List<CategoryVO> categoryList(Long shopId, Long parentId) {
        if (!Objects.equals(shopId, Constant.PLATFORM_SHOP_ID) || Objects.equals(parentId, Constant.CATEGORY_ID)) {
            return categoryMapper.listByShopIdAndParenId(shopId, parentId);
        }
        List<CategoryVO> categoryVOList  = categoryMapper.getCategoryAndChildCatogory(shopId, parentId);
        List<CategoryVO> categoryList = categoryVOList.stream().filter(c->c.getParentId().equals(parentId))
                .collect(Collectors.toList());
        setChildCategory(categoryList, categoryVOList);
        return categoryList;
    }

    @Override
    @Cacheable(cacheNames = CacheNames.CATEGORY_LIST_BY_PARENT_ID_AND_SHOP_ID, key = "#shopId + ':' + #parentId")
    public List<CategoryVO> listByShopIdAndParenId(Long shopId, Long parentId) {
        return categoryMapper.listByShopIdAndParenId(shopId, parentId);
    }

    @Override
    @Cacheable(cacheNames = CacheNames.CATEGORY_LIST_ALL_OF_SHOP, key = "#shopId")
    public List<CategoryVO> shopCategoryList(Long shopId) {
        List<CategoryVO> list = categoryMapper.listByShopIdAndParenId(shopId, null);
        Map<Integer, List<CategoryVO>> categoryMap = list.stream().collect(Collectors.groupingBy(CategoryVO::getLevel));

        List<CategoryVO> secondCategories = categoryMap.get(CategoryLevel.SECOND.value());
        if (Objects.equals(shopId, Constant.PLATFORM_SHOP_ID)) {
            // 三级分类
            List<CategoryVO> thirdCategories = categoryMap.get(CategoryLevel.THIRD.value());
            //二级分类
            setChildCategory(secondCategories, thirdCategories);
        }
        //一级分类
        List<CategoryVO> firstCategories = categoryMap.get(CategoryLevel.First.value());
        setChildCategory(firstCategories, secondCategories);
        return firstCategories;
    }

    @Override
    public List<Category> getChildCategory(Long categoryId) {
        return categoryMapper.getChildCategory(categoryId);
    }

    @Override
    public void updateBatchOfStatus(List<Long> updateList, Integer status) {
        if (CollUtil.isEmpty(updateList) || Objects.isNull(status)) {
            return;
        }
        categoryMapper.updateBatchOfStatus(updateList, status);
    }

    private void setChildCategory(List<CategoryVO> categories, List<CategoryVO> childCategories) {
        if (CollUtil.isEmpty(categories) || CollUtil.isEmpty(childCategories)) {
            return;
        }
        Map<Long, List<CategoryVO>> secondCategoryMap = childCategories.stream().collect(Collectors.groupingBy(CategoryVO::getParentId));
        for (CategoryVO category : categories) {
            category.setCategories(secondCategoryMap.get(category.getCategoryId()));
        }
    }

    /**
     * 校验分类名是否已存在
     *
     * @param category
     */
    private void existCategoryName(Category category) {
        category.setShopId(AuthUserContext.get().getTenantId());
        int countByName = categoryMapper.existCategoryName(category);
        if (countByName > 0) {
            throw new Mall4cloudException("分类名已存在，请重新输入");
        }
    }
}
