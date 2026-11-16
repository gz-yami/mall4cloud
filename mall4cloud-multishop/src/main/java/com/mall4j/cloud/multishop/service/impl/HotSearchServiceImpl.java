package com.mall4j.cloud.multishop.service.impl;

import com.mall4j.cloud.common.cache.constant.CacheNames;
import com.mall4j.cloud.common.database.dto.PageDTO;
import com.mall4j.cloud.common.database.util.PageUtil;
import com.mall4j.cloud.common.database.vo.PageVO;
import com.mall4j.cloud.multishop.dto.HotSearchDTO;
import com.mall4j.cloud.multishop.model.HotSearch;
import com.mall4j.cloud.multishop.mapper.HotSearchMapper;
import com.mall4j.cloud.multishop.service.HotSearchService;
import com.mall4j.cloud.multishop.vo.HotSearchVO;
import com.mall4j.cloud.common.exception.Mall4cloudException;
import com.mall4j.cloud.common.security.AuthUserContext;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

/**
 * 热搜
 *
 * @author YXF
 * @date 2021-01-27 09:10:00
 */
@Service
public class HotSearchServiceImpl implements HotSearchService {

    @Autowired
    private HotSearchMapper hotSearchMapper;

    @Override
    public PageVO<HotSearchVO> page(PageDTO pageDTO, HotSearchDTO hotSearchDTO) {
        return PageUtil.doPage(pageDTO, () -> hotSearchMapper.list(hotSearchDTO));
    }

    @Override
    public HotSearchVO getByHotSearchId(Long hotSearchId) {
        HotSearchVO hotSearch = hotSearchMapper.getByHotSearchId(hotSearchId);
        if (hotSearch == null || !Objects.equals(hotSearch.getShopId(), AuthUserContext.get().getTenantId())) {
            throw new Mall4cloudException("热搜不存在");
        }
        return hotSearch;
    }

    @Override
    public void save(HotSearch hotSearch) {
        hotSearchMapper.save(hotSearch);
    }

    @Override
    public void update(HotSearch hotSearch) {
        HotSearchVO dbHotSearch = hotSearchMapper.getByHotSearchId(hotSearch.getHotSearchId());
        if (dbHotSearch == null || !Objects.equals(dbHotSearch.getShopId(), hotSearch.getShopId())) {
            throw new Mall4cloudException("热搜不存在");
        }
        hotSearchMapper.update(hotSearch);
    }

    @Override
    public void deleteById(Long hotSearchId, Long shopId) {
        hotSearchMapper.deleteById(hotSearchId, shopId);
    }

    @Override
    @Cacheable(cacheNames = CacheNames.HOT_SEARCH_LIST_KEY, key = "#shopId")
    public List<HotSearchVO> listByShopId(Long shopId) {
        return hotSearchMapper.listByShopId(shopId);
    }

    @Override
    @CacheEvict(cacheNames = CacheNames.HOT_SEARCH_LIST_KEY, key = "#shopId")
    public void removeHotSearchListCache(Long shopId) {
    }
}
