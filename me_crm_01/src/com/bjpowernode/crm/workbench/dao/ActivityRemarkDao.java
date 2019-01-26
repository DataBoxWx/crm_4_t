package com.bjpowernode.crm.workbench.dao;

import java.util.List;

import com.bjpowernode.crm.domain.Activity;
import com.bjpowernode.crm.domain.ActivityRemark;

public interface ActivityRemarkDao {
	/**
	 * 根据activityID查询所有的备注
	 * @param id
	 * @return
	 */
	List<ActivityRemark> getAll(String id);
	
	/**
	 * 新增备注
	 * @param activityRemark
	 * @return
	 */
	int remarkSave(ActivityRemark activityRemark);
	
	/**
	 * 根据ID更新Activity
	 * @param activity
	 * @return
	 */
	int updateActivityById(Activity activity);

	/**
	 * 根据activityId删除备注
	 * @param id
	 * @return
	 */
	int deleteById(String id);

	/**
	 * 根据Id删除备注
	 * @param id
	 * @return
	 */
	int deleteRemarkById(String id);
	
	/**
	 * 更新remark
	 * @param activityRemark
	 * @return
	 */
	int updateRemark(ActivityRemark activityRemark);
}
