package com.mall4j.cloud.user.controller.app;

import com.mall4j.cloud.api.user.vo.AreaVO;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.user.service.AreaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 省市区地区信息
 *
 * @author YXF
 * @date 2020-11-25 14:48:52
 */
@RestController("appAreaController")
@RequestMapping("/area")
@Tag(name = "app-地区信息")
public class AreaController {

    @Autowired
    private AreaService areaService;

    @GetMapping("/list")
    @Operation(summary = "获取省市区地区信息列表" , description = "获取省市区地区信息列表")
    public ServerResponseEntity<List<AreaVO>> list() {
        List<AreaVO> list = areaService.getAreaListInfo();
        return ServerResponseEntity.success(list);
    }

    @GetMapping("/list_by_pid")
    @Operation(summary = "通过父级id获取区域列表" , description = "通过父级id获取区域列表")
    public ServerResponseEntity<List<AreaVO>> listByPid(Long pid) {
        List<AreaVO> list = areaService.listByPid(pid);
        return ServerResponseEntity.success(list);
    }
}
