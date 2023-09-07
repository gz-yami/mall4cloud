package com.mall4j.cloud.user.feign;

import com.mall4j.cloud.api.user.feign.UserFeignClient;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.user.service.UserService;
import com.mall4j.cloud.api.user.vo.UserApiVO;
import com.mall4j.cloud.common.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户地址feign连接
 * @author FrozenWatermelon
 * @date 2020/12/07
 */
@RestController
public class UserFeignController implements UserFeignClient {

    @Autowired
    private UserService userService;


    @Override
    public ServerResponseEntity<List<UserApiVO>> getUserByUserIds(List<Long> userIds) {
        List<UserApiVO> userList = userService.getUserByUserIds(userIds);
        return ServerResponseEntity.success(userList);
    }

    @Override
    public ServerResponseEntity<UserApiVO> getUserData(Long userId) {
        UserApiVO user = userService.getByUserId(userId);
        return ServerResponseEntity.success(user);
    }

}
