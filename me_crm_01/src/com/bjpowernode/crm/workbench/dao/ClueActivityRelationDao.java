package com.bjpowernode.crm.workbench.dao;

import java.util.List;

import com.bjpowernode.crm.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;

public interface ClueActivityRelationDao {

	List<Activity> getAllByClueId(String clueId);
	/**
	 * 根据id解除市场活动关联
	 * @param id
	 * @return
	 */
	int unbundRelation(String id);
	/**
	 * 根据市场活动和线索ID新增
	 * @param list
	 * @return
	 */
	int saveByActivityIdAndClueId(List<ClueActivityRelation> list);
	
	/**
	 * 根据线索id删除关系
	 * @param id
	 * @return
	 */
	int deleteByClueId(String id);
	
	/**
	 * 根据线索id查出市场活动id
	 * @param id
	 * @return
	 */
	List<String> getActivityIdByClueId(String id);

}
