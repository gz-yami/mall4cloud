package com.mall4j.cloud.user.service;

import com.mall4j.cloud.common.order.vo.UserAddrVO;
import com.mall4j.cloud.user.model.UserAddr;

import java.util.List;

/**
 * @Author lth
 * @Date 2021/7/1 17:37
 */
public interface UserAddrService {

    /**
     * 获取用户地址列表
     * @param userId
     * @return
     */
    List<UserAddrVO> list(Long userId);

    /**
     * 保存用户地址
     * @param userAddr 用户地址
     */
    void save(UserAddr userAddr);

    /**
     * 更新用户地址
     * @param userAddr 用户地址
     */
    void update(UserAddr userAddr);

    /**
     * 删除地址
     * @param addrId 地址id
     * @param userId 用户id
     */
    void deleteUserAddrByUserId(Long addrId, Long userId);

    /**
     * 根据用户地址id和用户id获取用户地址信息
     * @param addrId 地址id
     * @param userId 用户id
     * @return 用户地址信息
     */
    UserAddrVO getUserAddrByUserId(Long addrId, Long userId);

    /**
     * 用户地址的数量
     * @param userId 用户id
     * @return 数量
     */
    int countByUserId(Long userId);


    /**
     * 移除用户默认地址的缓存
     * @param userId 用户id
     */
    void removeUserDefaultAddrCacheByUserId(Long userId);
}
