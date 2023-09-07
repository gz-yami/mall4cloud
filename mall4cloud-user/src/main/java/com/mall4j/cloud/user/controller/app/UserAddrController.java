package com.mall4j.cloud.user.controller.app;

import com.mall4j.cloud.common.exception.Mall4cloudException;
import com.mall4j.cloud.common.order.vo.UserAddrVO;
import com.mall4j.cloud.common.response.ResponseEnum;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.common.security.AuthUserContext;
import com.mall4j.cloud.user.dto.UserAddrDTO;
import com.mall4j.cloud.user.model.UserAddr;
import com.mall4j.cloud.user.service.UserAddrService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import com.mall4j.cloud.common.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * @Author lth
 * @Date 2021/7/1 17:18
 */
@RestController("appUserAddrController")
@RequestMapping("/user_addr")
@Tag(name = "app-用户地址")
public class UserAddrController {

    @Autowired
    private UserAddrService userAddrService;



    private static final Integer MAX_USER_ADDR = 10;

    @GetMapping("/list")
    @Operation(summary = "获取用户地址列表" , description = "获取用户地址列表")
    public ServerResponseEntity<List<UserAddrVO>> list() {
        Long userId = AuthUserContext.get().getUserId();
        List<UserAddrVO> userAddrPage = userAddrService.list(userId);
        return ServerResponseEntity.success(userAddrPage);
    }

    @GetMapping
    @Operation(summary = "获取用户地址" , description = "根据addrId获取用户地址")
    public ServerResponseEntity<UserAddrVO> getByAddrId(@RequestParam Long addrId) {
        return ServerResponseEntity.success(userAddrService.getUserAddrByUserId(addrId,AuthUserContext.get().getUserId()));
    }

    @PostMapping
    @Operation(summary = "保存用户地址" , description = "保存用户地址")
    public ServerResponseEntity<Void> save(@Valid @RequestBody UserAddrDTO userAddrDTO) {
        Long userId = AuthUserContext.get().getUserId();
        int userAddrCount = userAddrService.countByUserId(userId);
        if (userAddrCount >= MAX_USER_ADDR) {
            return ServerResponseEntity.showFailMsg("收货地址已达到上限，无法再新增地址");
        }
        UserAddr userAddr = BeanUtil.map(userAddrDTO, UserAddr.class);
        if (userAddrCount == 0) {
            userAddr.setIsDefault(UserAddr.DEFAULT_ADDR);
        } else if (!UserAddr.DEFAULT_ADDR.equals(userAddr.getIsDefault())){
            userAddr.setIsDefault(UserAddr.NOT_DEFAULT_ADDR);
        }
        userAddr.setAddrId(null);
        userAddr.setUserId(userId);
        userAddrService.save(userAddr);
        // 清除默认地址缓存
        if (UserAddr.DEFAULT_ADDR.equals(userAddr.getIsDefault())) {
            userAddrService.removeUserDefaultAddrCacheByUserId(userId);
        }
        return ServerResponseEntity.success();
    }

    @PutMapping
    @Operation(summary = "更新用户地址" , description = "更新用户地址")
    public ServerResponseEntity<Void> update(@Valid @RequestBody UserAddrDTO userAddrDTO) {
        Long userId = AuthUserContext.get().getUserId();
        UserAddrVO dbUserAddr = userAddrService.getUserAddrByUserId(userAddrDTO.getAddrId(), userId);
        if (dbUserAddr == null) {
            throw new Mall4cloudException("该地址已被删除");
        }
        // 默认地址不能修改为普通地址
        else if (dbUserAddr.getIsDefault().equals(UserAddr.DEFAULT_ADDR) && userAddrDTO.getIsDefault().equals(UserAddr.NOT_DEFAULT_ADDR)) {
            throw new Mall4cloudException(ResponseEnum.DATA_ERROR);
        }
        UserAddr userAddr = BeanUtil.map(userAddrDTO, UserAddr.class);
        userAddr.setUserId(userId);
        userAddrService.update(userAddr);
        // 清除默认地址缓存
        if (userAddr.getIsDefault().equals(UserAddr.DEFAULT_ADDR)) {
            userAddrService.removeUserDefaultAddrCacheByUserId(userId);
        }
        return ServerResponseEntity.success();
    }

    @DeleteMapping
    @Operation(summary = "删除用户地址" , description = "根据用户地址id删除用户地址")
    public ServerResponseEntity<Void> delete(@RequestParam Long addrId) {
        Long userId = AuthUserContext.get().getUserId();
        UserAddrVO dbUserAddr = userAddrService.getUserAddrByUserId(addrId, userId);
        if (dbUserAddr == null) {
            throw new Mall4cloudException("该地址已被删除");
        } else if (dbUserAddr.getIsDefault().equals(UserAddr.DEFAULT_ADDR)) {
            throw new Mall4cloudException("默认地址不能删除");
        }
        userAddrService.deleteUserAddrByUserId(addrId, userId);
        return ServerResponseEntity.success();
    }

}
