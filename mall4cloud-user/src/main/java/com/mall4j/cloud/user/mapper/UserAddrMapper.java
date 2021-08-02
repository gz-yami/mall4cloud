package com.mall4j.cloud.user.mapper;

import com.mall4j.cloud.common.order.vo.UserAddrVO;
import com.mall4j.cloud.user.model.UserAddr;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author lth
 * @Date 2021/7/1 17:42
 */
public interface UserAddrMapper {

    /**
     * 获取用户地址列表
     *
     * @param userId
     * @return 用户地址列表
     */
    List<UserAddrVO> list(@Param("userId") Long userId);

    /**
     * 根据用户地址id获取用户地址
     *
     * @param userId 用户id
     * @param addrId 用户地址id
     * @return 用户地址
     */
    UserAddrVO getByAddrId(@Param("addrId") Long addrId, @Param("userId") Long userId);

    /**
     * 保存用户地址
     *
     * @param userAddr 用户地址
     */
    void save(@Param("userAddr") UserAddr userAddr);

    /**
     * 更新用户地址
     *
     * @param userAddr 用户地址
     */
    void update(@Param("userAddr") UserAddr userAddr);

    /**
     * 删除地址
     *
     * @param addrId 地址id
     * @param userId 用户id
     */
    void deleteById(@Param("addrId") Long addrId, @Param("userId") Long userId);


    /**
     * 移除用户默认地址
     *
     * @param userId
     */
    void removeDefaultUserAddr(@Param("userId") Long userId);

    /**
     * 将地址设置为默认地址
     *
     * @param addrId 地址id
     * @param userId 用户id
     */
    void setDefaultUserAddr(@Param("addrId") Long addrId, @Param("userId") Long userId);

    /**
     * 用户地址的数量
     *
     * @param userId 用户id
     * @return 数量
     */
    int countByUserId(Long userId);

    /**
     * 通过用户id获取默认地址
     * @param userId 用户id
     * @return 默认地址
     */
    UserAddrVO getUserDefaultAddrByUserId(@Param("userId") Long userId);
}
