package com.mall4j.cloud.product.service;

import com.mall4j.cloud.api.product.vo.CategoryVO;
import com.mall4j.cloud.product.dto.CategoryDTO;
import com.mall4j.cloud.product.model.Category;

import java.util.List;

/**
 * 分类信息
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
public interface CategoryAndSpuService {
	/**
	 * 分类的启用和禁用
	 * @param categoryDTO
	 * @return
	 */
    Boolean categoryEnableOrDisable(CategoryDTO categoryDTO);

}
