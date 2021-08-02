package com.mall4j.cloud.leaf.segment.dao;

import com.mall4j.cloud.leaf.segment.model.LeafAlloc;

import java.util.List;

/**
 * @author leaf
 */
public interface IDAllocDao {

	/**
	 * com.mall4j.cloud.leaf.segment.dao.IDAllocMapper.getAllLeafAllocs
	 * @return List<LeafAlloc>
	 */
	List<LeafAlloc> getAllLeafAllocs();

	/**
	 * updateMaxIdAndGetLeafAlloc
	 * @param tag tag
	 * @return LeafAlloc
	 */
	LeafAlloc updateMaxIdAndGetLeafAlloc(String tag);

	/**
	 * updateMaxIdByCustomStepAndGetLeafAlloc
	 * @param leafAlloc leafAlloc
	 * @return LeafAlloc
	 */
	LeafAlloc updateMaxIdByCustomStepAndGetLeafAlloc(LeafAlloc leafAlloc);

	/**
	 * getAllTags
	 * @return List<String>
	 */
	List<String> getAllTags();

}
