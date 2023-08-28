package com.mall4j.cloud.biz.controller.multishop;


import com.mall4j.cloud.biz.dto.AttachFileDTO;
import com.mall4j.cloud.biz.model.AttachFile;
import com.mall4j.cloud.biz.service.AttachFileService;
import com.mall4j.cloud.biz.vo.AttachFileVO;
import com.mall4j.cloud.common.database.dto.PageDTO;
import com.mall4j.cloud.common.database.vo.PageVO;
import com.mall4j.cloud.common.exception.Mall4cloudException;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import com.mall4j.cloud.common.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * 上传文件记录表
 *
 * @author YXF
 * @date 2020-11-21 10:21:40
 */
@RestController("multishopAttachFileController")
@RequestMapping("/m/attach_file")
@Tag(name = "上传文件记录表")
public class AttachFileController {

    @Autowired
    private AttachFileService attachFileService;



    @GetMapping("/page")
    @Operation(summary = "获取上传文件记录表列表" , description = "分页获取上传文件记录表列表")
    public ServerResponseEntity<PageVO<AttachFileVO>> page(@Valid PageDTO pageDTO, String fileName, Long fileGroupId) {
        if (fileGroupId == 0) {
            fileGroupId = null;
        }
        PageVO<AttachFileVO> attachFilePage = attachFileService.page(pageDTO, fileName, fileGroupId);
        return ServerResponseEntity.success(attachFilePage);
    }

    @PostMapping
    @Operation(summary = "保存上传文件记录" , description = "保存上传文件记录")
    public ServerResponseEntity<Void> save(@RequestBody List<AttachFileDTO> attachFileDtos) {
        List<AttachFile> attachFiles = BeanUtil.mapAsList(attachFileDtos, AttachFile.class);
        attachFileService.save(attachFiles);
        return ServerResponseEntity.success();
    }

    /**
     * 更改文件名或分组
     */
    @PutMapping("/update_file")
    @Operation(summary = "更新文件记录" , description = "更新文件记录")
    public ServerResponseEntity<Boolean> updateFileName(@RequestBody AttachFileDTO attachFileDto) {
        if (Objects.isNull(attachFileDto.getFileName())) {
            // 图片名称不能为空
            throw new Mall4cloudException("图片名称不能为空");
        }
        AttachFile attachFile = BeanUtil.map(attachFileDto, AttachFile.class);
        return ServerResponseEntity.success(attachFileService.updateFileName(attachFile));
    }

    @DeleteMapping
    @Operation(summary = "删除上传文件记录" , description = "根据上传文件记录表id删除上传文件记录")
    public ServerResponseEntity<Void> delete(@RequestParam Long fileId) {
        attachFileService.deleteById(fileId);
        return ServerResponseEntity.success();
    }
}
