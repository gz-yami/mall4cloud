package com.mall4j.cloud.biz.service.impl;

import com.mall4j.cloud.biz.mapper.AttachFileMapper;
import com.mall4j.cloud.biz.model.AttachFileGroup;
import com.mall4j.cloud.biz.mapper.AttachFileGroupMapper;
import com.mall4j.cloud.biz.service.AttachFileGroupService;
import com.mall4j.cloud.biz.vo.AttachFileGroupVO;
import com.mall4j.cloud.common.exception.Mall4cloudException;
import com.mall4j.cloud.common.security.AuthUserContext;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 *
 *
 * @author YXF
 * @date 2020-12-04 16:15:02
 */
@Service
public class AttachFileGroupServiceImpl implements AttachFileGroupService {

    @Autowired
    private AttachFileMapper attachFileMapper;
    @Autowired
    private AttachFileGroupMapper attachFileGroupMapper;

    @Override
    public List<AttachFileGroupVO> list() {
        return attachFileGroupMapper.list(AuthUserContext.get().getTenantId());
    }

    @Override
    public AttachFileGroupVO getByAttachFileGroupId(Long attachFileGroupId) {
        AttachFileGroupVO attachFileGroup = attachFileGroupMapper.getByAttachFileGroupId(attachFileGroupId);
        if (attachFileGroup == null || !Objects.equals(attachFileGroup.getShopId(), AuthUserContext.get().getTenantId())) {
            return null;
        }
        return attachFileGroup;
    }

    @Override
    public void save(AttachFileGroup attachFileGroup) {
        attachFileGroup.setShopId(AuthUserContext.get().getTenantId());
        attachFileGroupMapper.save(attachFileGroup);
    }

    @Override
    public void update(AttachFileGroup attachFileGroup) {
        AttachFileGroupVO dbAttachFileGroup = getOwnGroup(attachFileGroup.getAttachFileGroupId());
        attachFileGroup.setShopId(dbAttachFileGroup.getShopId());
        attachFileGroupMapper.update(attachFileGroup);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long attachFileGroupId) {
        getOwnGroup(attachFileGroupId);
        attachFileGroupMapper.deleteById(attachFileGroupId);
        attachFileMapper.updateBatchByAttachFileGroupId(attachFileGroupId);
    }

    private AttachFileGroupVO getOwnGroup(Long attachFileGroupId) {
        AttachFileGroupVO attachFileGroup = attachFileGroupMapper.getByAttachFileGroupId(attachFileGroupId);
        if (attachFileGroup == null || !Objects.equals(attachFileGroup.getShopId(), AuthUserContext.get().getTenantId())) {
            throw new Mall4cloudException("分组不存在");
        }
        return attachFileGroup;
    }
}
