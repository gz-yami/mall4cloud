package com.mall4j.cloud.user.controller.app;

import com.mall4j.cloud.api.auth.bo.UserInfoInTokenBO;
import com.mall4j.cloud.api.user.vo.UserApiVO;
import com.mall4j.cloud.common.response.ResponseEnum;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.common.security.AuthUserContext;
import com.mall4j.cloud.user.model.User;
import com.mall4j.cloud.user.service.UserService;
import com.mall4j.cloud.user.vo.UserSimpleInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * 用户地址
 *
 * @author FrozenWatermelon
 * @date 2020-12-07 15:50:02
 */
@RestController("appUserController")
@RequestMapping("/a/user")
@Api(tags = "app-用户信息")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/simple_info")
    @ApiOperation(value="用户头像昵称", notes="用户头像昵称")
    public ServerResponseEntity<UserSimpleInfoVO> getByAddrId() {
        Long userId = AuthUserContext.get().getUserId();

        UserApiVO userApiVO = userService.getByUserId(userId);
        UserSimpleInfoVO userSimpleInfoVO = new UserSimpleInfoVO();
        userSimpleInfoVO.setNickName(userApiVO.getNickName());
        userSimpleInfoVO.setPic(userApiVO.getPic());

        return ServerResponseEntity.success(userSimpleInfoVO);
    }


    @GetMapping("/ma/user_detail_info")
    @ApiOperation(value = "获取用户详细信息", notes = "返回用户详细信息")
    public ServerResponseEntity<UserApiVO> getUserDetailInfo() {
        UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();
        if (userInfoInTokenBO == null) {
            return ServerResponseEntity.fail(ResponseEnum.CLEAN_TOKEN);
        }
        Long userId = userInfoInTokenBO.getUserId();
        UserApiVO userApiVO = userService.getByUserId(userId);
        return ServerResponseEntity.success(userApiVO);
    }

    @PostMapping ("/ma/update_user")
    @ApiOperation(value = "更新用户信息")
    public ServerResponseEntity<Void> updateUser(@RequestBody UserApiVO userApiVO) {
        Long userId = AuthUserContext.get().getUserId();
        UserApiVO byUserId = userService.getByUserId(userId);
        User user = new User();
        user.setUserId(userId);
        user.setNickName(Objects.isNull(userApiVO.getNickName())? byUserId.getNickName() : userApiVO.getNickName());
        user.setPic(Objects.isNull(userApiVO.getPic())? byUserId.getPic() : userApiVO.getPic());
        userService.update(user);
        return ServerResponseEntity.success();
    }
}
