package com.mall4j.cloud.biz.service;

import com.mall4j.cloud.biz.model.AttachFile;
import com.mall4j.cloud.biz.vo.AttachFileVO;
import com.mall4j.cloud.common.database.dto.PageDTO;
import com.mall4j.cloud.common.database.vo.PageVO;

import java.util.List;

/**
 * 上传文件记录表
 *
 * @author YXF
 * @date 2020-11-21 10:21:40
 */
public interface AttachFileService {

	/**
	 * 分页获取上传文件记录表列表
	 * @param pageDTO 分页参数
	 * @param fileName 文件名
	 * @param fileGroupId 文件分组id
	 * @return 上传文件记录表列表分页数据
	 */
	PageVO<AttachFileVO> page(PageDTO pageDTO, String fileName, Long fileGroupId);

	/**
	 * 保存上传文件记录表
	 * @param attachFiles 上传文件记录表
	 */
	void save(List<AttachFile> attachFiles);

	/**
	 * 更新上传文件记录表
	 * @param attachFile 上传文件记录表
	 */
	void update(AttachFile attachFile);

	/**
	 * 根据上传文件记录表id删除上传文件记录表
	 * @param fileId
	 */
	void deleteById(Long fileId);

	/**
	 * 更新文件名称
	 * @param attachFile
	 * @return
	 */
	Boolean updateFileName(AttachFile attachFile);
}
