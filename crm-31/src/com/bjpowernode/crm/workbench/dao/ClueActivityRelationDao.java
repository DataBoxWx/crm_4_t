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

}
