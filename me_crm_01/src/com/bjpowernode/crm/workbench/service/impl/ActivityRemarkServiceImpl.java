package com.bjpowernode.crm.workbench.service.impl;

import java.util.List;

import com.bjpowernode.crm.domain.Activity;
import com.bjpowernode.crm.domain.ActivityRemark;
import com.bjpowernode.crm.util.SqlSessionUtil;
import com.bjpowernode.crm.workbench.dao.ActivityDao;
import com.bjpowernode.crm.workbench.dao.ActivityRemarkDao;
import com.bjpowernode.crm.workbench.service.ActivityRemarkService;

public class ActivityRemarkServiceImpl implements ActivityRemarkService {
	private ActivityDao activityDao = SqlSessionUtil.getCurrentSqlSession().getMapper(ActivityDao.class);
	private ActivityRemarkDao activityRemarkDao = SqlSessionUtil.getCurrentSqlSession().getMapper(ActivityRemarkDao.class);
	
	@Override
	public Activity getActivityById(String id) {
		return activityDao.getActivityById(id);
	}
	
	@Override
	public List<ActivityRemark> getAll(String id) {
		
		return activityRemarkDao.getAll(id);
	}
	
	@Override
	public int remarkSave(ActivityRemark activityRemark) {
		
		return activityRemarkDao.remarkSave(activityRemark);
	}

	@Override
	public int updateActivityById(Activity activity) {
		
		return activityRemarkDao.updateActivityById(activity);
	}

	@Override
	public int deleteById(String id) {
		
		return activityDao.deleteById(id) + activityRemarkDao.deleteById(id);
	}

	@Override
	public int deleteRemarkById(String id) {
		
		return activityRemarkDao.deleteRemarkById(id);
	}

	@Override
	public int updateRemark(ActivityRemark activityRemark) {
		
		return activityRemarkDao.updateRemark(activityRemark);
	}

}
