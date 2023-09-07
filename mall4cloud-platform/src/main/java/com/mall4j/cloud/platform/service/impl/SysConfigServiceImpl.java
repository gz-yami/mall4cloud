package com.mall4j.cloud.platform.service.impl;

import cn.hutool.core.util.StrUtil;
import com.mall4j.cloud.common.cache.constant.ConfigCacheNames;
import com.mall4j.cloud.common.util.Json;
import com.mall4j.cloud.platform.model.SysConfig;
import com.mall4j.cloud.platform.mapper.SysConfigMapper;
import com.mall4j.cloud.platform.service.SysConfigService;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 系统配置信息表
 *
 * @author lhd
 * @date 2020-12-23 16:27:57
 */
@Service
public class SysConfigServiceImpl implements SysConfigService {

    @Resource
    private SysConfigMapper sysConfigMapper;

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames= ConfigCacheNames.SYS_CONFIG_OBJECT,key="#key"),
            @CacheEvict(cacheNames= ConfigCacheNames.SYS_CONFIG,key="#key")
    })
    public void updateValueByKey(String key, String value) {
//        sysConfigMapper.updateValueByKey(key, value);
    }

    @Override
    public void deleteBatch(Long[] ids) {
//        sysConfigMapper.deleteBatch(ids);
    }

    @Override
    @Cacheable(cacheNames = ConfigCacheNames.SYS_CONFIG, key = "#key")
    public String getValue(String key) {
        SysConfig config = sysConfigMapper.queryByKey(key);
        return config == null ? null : config.getParamValue();
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = ConfigCacheNames.SYS_CONFIG_OBJECT,key="#key"),
            @CacheEvict(cacheNames = ConfigCacheNames.SYS_CONFIG,key="#key")
    })
    public void removeSysConfig(String key) {
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = ConfigCacheNames.SYS_CONFIG_OBJECT,key="#sysConfig.paramKey"),
            @CacheEvict(cacheNames = ConfigCacheNames.SYS_CONFIG,key="#sysConfig.paramKey")
    })
    public void saveOrUpdateSysConfig(SysConfig sysConfig) {
        if (sysConfigMapper.countByKey(sysConfig.getParamKey()) > 0) {
            sysConfigMapper.update(sysConfig);
        }else{
            sysConfigMapper.save(sysConfig);
        }
    }

    @Override
    @Cacheable(cacheNames = ConfigCacheNames.SYS_CONFIG, key = "#key")
    public SysConfig getByKey(String key) {
        return sysConfigMapper.queryByKey(key);
    }


    @Override
    @Cacheable(cacheNames = ConfigCacheNames.SYS_CONFIG_OBJECT,key="#key")
    public <T> T  getSysConfigObject(String key, Class<T> clazz) {
        String value = getValue(key);
        if (StrUtil.isBlank(value)) {
            return null;
        }

        if(Objects.equals(String.class,clazz)){
            return (T)value;
        }else{
            return Json.parseObject(value, clazz);
        }
    }
}
