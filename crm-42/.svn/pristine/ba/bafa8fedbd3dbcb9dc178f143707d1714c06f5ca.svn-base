package com.bjpowernode.crm.workbench.dao;

import java.util.List;

import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;

public interface ClueActivityRelationDao {
	
	/**
	 * 
	 * @param clueId
	 * @return
	 */
	List<Activity> getActivityByClueId(String clueId);

	/**
	 * 
	 * @param relationId
	 * @return
	 */
	int deleteById(String relationId);

	/**
	 * 
	 * @param carList
	 * @return
	 */
	int save(List<ClueActivityRelation> carList);

	/**
	 * 根据线索id获取关联的市场活动id
	 * @param clueId
	 * @return
	 */
	List<String> getActivityIdByClueId(String clueId);

	/**
	 * 根据线索id删除线索和市场活动的关系
	 * @param clueId
	 */
	void deleteByClueId(String clueId);

}
