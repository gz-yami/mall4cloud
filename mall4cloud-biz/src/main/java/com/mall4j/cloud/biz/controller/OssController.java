package com.mall4j.cloud.biz.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.mall4j.cloud.biz.config.MinioTemplate;
import com.mall4j.cloud.biz.config.OssConfig;
import com.mall4j.cloud.biz.constant.OssType;
import com.mall4j.cloud.biz.vo.OssVO;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author FrozenWatermelon
 * @date 2020/9/10
 */
@RequestMapping(value = "/oss")
@RestController
@Api(tags = "文件管理")
public class OssController {

    private static final Logger log = LoggerFactory.getLogger(OssController.class);

    /**
     * 上传的文件夹(根据时间确定)
     */
    public static final String NORM_DAY_PATTERN = "yyyy/MM/dd";

    @Autowired
    private OssConfig ossConfig;
    @Autowired
    private MinioTemplate minioTemplate;


    @GetMapping(value = "/info")
    @ApiOperation(value = "token", notes = "获取文件上传需要的token")
    @ApiImplicitParam(name = "fileNum", value = "需要获取token的文件数量", defaultValue = "0")
    public ServerResponseEntity<OssVO> info(@RequestParam("fileNum") Integer fileNum) {
        OssVO ossVO = new OssVO();
        // minio文件上传
        if (Objects.equals(ossConfig.getOssType(), OssType.MINIO.value())) {
            fillMinIoInfo(ossVO, fileNum);
        }
        return ServerResponseEntity.success(ossVO);
    }

    private void fillMinIoInfo(OssVO ossVo, Integer fileNum) {
        List<OssVO> ossVOList = new ArrayList<>();
        for (int i = 0; i<fileNum; i++) {
            OssVO oss = loadOssVO(new OssVO());
            String actionUrl = minioTemplate.getPresignedObjectUrl(oss.getDir() + oss.getFileName());
            oss.setActionUrl(actionUrl);
            ossVOList.add(oss);
        }
        ossVo.setOssList(ossVOList);
    }

    private OssVO loadOssVO(OssVO ossVo) {
        String dir = DateUtil.format(new Date(), NORM_DAY_PATTERN)+ "/";
        String fileName = IdUtil.simpleUUID();
        ossVo.setDir(dir);
        ossVo.setFileName(fileName);
        return ossVo;
    }


    @PostMapping("/upload_minio")
    @ApiOperation(value = "文件上传接口", notes = "上传文件，返回文件路径与域名")
    public ServerResponseEntity<OssVO> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return ServerResponseEntity.success();
        }
        OssVO oss = loadOssVO(new OssVO());
        minioTemplate.uploadMinio(file.getBytes(), oss.getDir() + oss.getFileName(), file.getContentType());
        return ServerResponseEntity.success(oss);
    }


}
