package com.mall4j.cloud.biz.mapper;

import com.mall4j.cloud.biz.model.AttachFileGroup;
import com.mall4j.cloud.biz.vo.AttachFileGroupVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author YXF
 * @date 2020-12-04 16:15:02
 */
public interface AttachFileGroupMapper {

	/**
	 * 获取列表
	 * @param shopId
	 * @return 列表
	 */
	List<AttachFileGroupVO> list(@Param("shopId") Long shopId);

	/**
	 * 根据id获取
	 *
	 * @param attachFileGroupId id
	 * @return
	 */
	AttachFileGroupVO getByAttachFileGroupId(@Param("attachFileGroupId") Long attachFileGroupId);

	/**
	 * 保存
	 *
	 * @param attachFileGroup
	 */
	void save(@Param("attachFileGroup") AttachFileGroup attachFileGroup);

	/**
	 * 更新
	 *
	 * @param attachFileGroup
	 */
	void update(@Param("attachFileGroup") AttachFileGroup attachFileGroup);

	/**
	 * 根据id删除
	 *
	 * @param attachFileGroupId
	 */
	void deleteById(@Param("attachFileGroupId") Long attachFileGroupId);
}
