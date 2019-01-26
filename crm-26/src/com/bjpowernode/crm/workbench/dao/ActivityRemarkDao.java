package com.bjpowernode.crm.workbench.dao;

import java.util.List;

import com.bjpowernode.crm.workbench.domain.ActivityRemark;

public interface ActivityRemarkDao {

	/**
	 * 
	 * @param activityId
	 * @return
	 */
	List<ActivityRemark> getByActivityId(String activityId);

	/**
	 * 
	 * @param ar
	 * @return
	 */
	int save(ActivityRemark ar);

	/**
	 * 根据id删除市场活动备注
	 * @param id
	 * @return
	 */
	int deleteById(String id);

	/**
	 * 
	 * @param ar
	 * @return
	 */
	int update(ActivityRemark ar);

}
