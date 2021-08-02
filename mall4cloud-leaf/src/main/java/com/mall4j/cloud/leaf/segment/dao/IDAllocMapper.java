package com.mall4j.cloud.leaf.segment.dao;

import com.mall4j.cloud.leaf.segment.model.LeafAlloc;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author leaf
 */
public interface IDAllocMapper {

	/**
	 * getAllLeafAllocs
	 * @return List<LeafAlloc>
	 */
	@Select("SELECT biz_tag, max_id, step, update_time FROM leaf_alloc")
	@Results(value = { @Result(column = "biz_tag", property = "key"), @Result(column = "max_id", property = "maxId"),
			@Result(column = "step", property = "step"), @Result(column = "update_time", property = "updateTime") })
	List<LeafAlloc> getAllLeafAllocs();

	/**
	 * getLeafAlloc
	 * @param tag tag
	 * @return LeafAlloc
	 */
	@Select("SELECT biz_tag, max_id, step, random_step FROM leaf_alloc WHERE biz_tag = #{tag}")
	@Results(value = { @Result(column = "biz_tag", property = "key"), @Result(column = "max_id", property = "maxId"),
			@Result(column = "step", property = "step"), @Result(column = "random_step", property = "randomStep") })
	LeafAlloc getLeafAlloc(@Param("tag") String tag);

	/**
	 * updateMaxId
	 * @param tag tag
	 */
	@Update("UPDATE leaf_alloc SET max_id = max_id + step WHERE biz_tag = #{tag}")
	void updateMaxId(@Param("tag") String tag);

	/**
	 * updateMaxIdByCustomStep
	 * @param leafAlloc leafAlloc
	 */
	@Update("UPDATE leaf_alloc SET max_id = max_id + #{step} WHERE biz_tag = #{key}")
	void updateMaxIdByCustomStep(@Param("leafAlloc") LeafAlloc leafAlloc);

	/**
	 * getAllTags
	 * @return List<String>
	 */
	@Select("SELECT biz_tag FROM leaf_alloc")
	List<String> getAllTags();

}
