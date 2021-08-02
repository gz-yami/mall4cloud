package com.mall4j.cloud.biz.service.impl;

import cn.hutool.core.util.StrUtil;
import com.mall4j.cloud.biz.config.MinioTemplate;
import com.mall4j.cloud.biz.mapper.AttachFileMapper;
import com.mall4j.cloud.biz.model.AttachFile;
import com.mall4j.cloud.biz.service.AttachFileService;
import com.mall4j.cloud.biz.vo.AttachFileVO;
import com.mall4j.cloud.common.database.dto.PageDTO;
import com.mall4j.cloud.common.database.util.PageUtil;
import com.mall4j.cloud.common.database.vo.PageVO;
import com.mall4j.cloud.common.security.AuthUserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;

/**
 * 上传文件记录表
 *
 * @author YXF
 * @date 2020-11-21 10:21:40
 */
@Service
public class AttachFileServiceImpl implements AttachFileService {

    @Autowired
    private AttachFileMapper attachFileMapper;

    @Autowired
    private Environment environment;

    @Autowired
    private MinioTemplate minioTemplate;

    @Override
    public PageVO<AttachFileVO> page(PageDTO pageDTO, String fileName, Long fileGroupId) {
        return PageUtil.doPage(pageDTO, () -> attachFileMapper.list(fileName, AuthUserContext.get().getTenantId(), fileGroupId));
    }

    @Override
    public void save(List<AttachFile> attachFiles) {
        attachFileMapper.save(attachFiles, AuthUserContext.get().getTenantId());
    }

    @Override
    public void update(AttachFile attachFile) {
        attachFileMapper.update(attachFile);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long fileId) {
        AttachFile attachFile = attachFileMapper.getById(fileId);
        deleteFile(attachFile.getFilePath());
        attachFileMapper.deleteById(fileId);
    }

    @Override
    public Boolean updateFileName(AttachFile attachFile) {
        if (Objects.isNull(attachFile.getFileName()) && Objects.isNull(attachFile.getAttachFileGroupId())) {
            return Boolean.TRUE;
        }
        attachFileMapper.update(attachFile);
        return Boolean.TRUE;
    }

    /**
     * 根据文件路径，删除文件
     * @param filePath 文件路径
     */
    public void deleteFile(String filePath){
        // 获取文件的实际路径--数据库中保存的文件路径为： / + 实际的文件路径
        if (StrUtil.isNotBlank(filePath)) {
            filePath = filePath.substring(1);
        }
        try {
            minioTemplate.removeObject(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
