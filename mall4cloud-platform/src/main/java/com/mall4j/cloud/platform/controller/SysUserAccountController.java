package com.mall4j.cloud.platform.controller;

import com.mall4j.cloud.api.auth.vo.AuthAccountVO;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.common.security.AuthUserContext;
import com.mall4j.cloud.platform.dto.ChangeAccountDTO;
import com.mall4j.cloud.platform.service.SysUserAccountService;
import com.mall4j.cloud.platform.service.SysUserService;
import com.mall4j.cloud.platform.vo.SysUserVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Objects;

/**
 * @author lhd
 * @date 2020/12/21
 */
@RequestMapping(value = "/sys_user/account")
@RestController
@Tag(name = "平台用户账号信息")
public class SysUserAccountController {

	private final SysUserAccountService SysUserAccountService;

	private final SysUserService SysUserService;

	public SysUserAccountController(SysUserAccountService sysUserAccountService, SysUserService sysUserService) {
		this.SysUserAccountService = sysUserAccountService;
		this.SysUserService = sysUserService;
	}


	@GetMapping
	@Operation(summary = "获取账号信息" , description = "获取账号信息")
	public ServerResponseEntity<AuthAccountVO> getAccount(Long userId) {
		return SysUserAccountService.getByUserIdAndSysType(userId, AuthUserContext.get().getSysType());
	}


	@PostMapping
	@Operation(summary = "添加账号" , description = "添加账号")
	public ServerResponseEntity<Void> addAccount(@Valid @RequestBody ChangeAccountDTO changeAccountDTO) {
		SysUserVO sysUserVO = SysUserService.getByUserId(changeAccountDTO.getUserId());
		if (sysUserVO == null) {
			return ServerResponseEntity.showFailMsg("无法获取账户信息");
		}
		if (Objects.equals(sysUserVO.getHasAccount(), 1)) {
			return ServerResponseEntity.showFailMsg("已有账号，无需重复添加");
		}
		return SysUserAccountService.save(changeAccountDTO);
	}

	@PutMapping
	@Operation(summary = "修改账号" , description = "修改账号")
	public ServerResponseEntity<Void> updateAccount(@Valid @RequestBody ChangeAccountDTO changeAccountDTO) {
		SysUserVO sysUserVO = SysUserService.getByUserId(changeAccountDTO.getUserId());
		if (sysUserVO == null || Objects.equals(sysUserVO.getHasAccount(), 0)) {
			return ServerResponseEntity.showFailMsg("无法获取账户信息");
		}
		return SysUserAccountService.update(changeAccountDTO);
	}
}
