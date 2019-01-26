package com.bjpowernode.crm.workbench.service.impl;

import java.util.List;

import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.workbench.dao.ActivityRemarkDao;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.service.ActivityRemarkService;

public class ActivityRemarkServiceImpl implements ActivityRemarkService {

	private ActivityRemarkDao ard = SqlSessionUtil.getCurrentSqlSession().getMapper(ActivityRemarkDao.class);
	
	@Override
	public List<ActivityRemark> getByActivityId(String activityId) {
		return ard.getByActivityId(activityId);
	}

	@Override
	public int save(ActivityRemark ar) {
		return ard.save(ar);
	}

}
