package com.bjpowernode.crm.workbench.service;

import java.util.List;

import com.bjpowernode.crm.workbench.domain.ActivityRemark;

public interface ActivityRemarkService {

	/**
	 * 根据市场活动id获取该市场活动下所有的备注信息
	 * @param activityId
	 * @return
	 */
	List<ActivityRemark> getByActivityId(String activityId);

	/**
	 * 保存备注
	 * @param ar
	 * @return
	 */
	int save(ActivityRemark ar);

	/**
	 * 根据id删除备注
	 * @param id
	 * @return
	 */
	boolean deleteById(String id);

	/**
	 * 更新市场活动备注
	 * @param ar
	 * @return
	 */
	boolean update(ActivityRemark ar);

}
