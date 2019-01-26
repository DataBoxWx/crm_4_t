package com.bjpowernode.crm.workbench.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bjpowernode.crm.domain.Activity;
import com.bjpowernode.crm.domain.ActivityRemark;
import com.bjpowernode.crm.util.SqlSessionUtil;
import com.bjpowernode.crm.workbench.dao.ActivityDao;
import com.bjpowernode.crm.workbench.service.ActivityService;

public class ActivityServiceImpl implements ActivityService {
	private ActivityDao activityDao = SqlSessionUtil.getCurrentSqlSession().getMapper(ActivityDao.class);
	@Override
	public int save(Activity activity) {
		return activityDao.save(activity);
	}
	@Override
	public Map<String, Object> doList(Map<String, Object> map) {
		Map<String, Object> mapRe = new HashMap<String, Object>();
		//总条数
		int total = activityDao.pageSum(map);
		//列表
		List<Activity> list = activityDao.list(map);
		mapRe.put("total", total);
		mapRe.put("list", list);
		return mapRe;
	}
	@Override
	public Activity edit(String id) {
		
		return activityDao.edit(id);
	}
	@Override
	public Boolean update(Activity activity) {
		
		return (activityDao.update(activity) == 1 ? true : false);
	}
	@Override
	public Boolean dodelete(String[] ids) {
		
		return (activityDao.delete(ids) != 0 ? true : false);
	}
	@Override
	public List<Activity> getAllActivity() {
		
		return activityDao.getAll();
	}
	@Override
	public List<Activity> getActivityById(String[] ids) {
		// TODO Auto-generated method stub
		return activityDao.getActivityById2(ids);
	}
	@Override
	public int saves(List<Activity> activitieList) {
		
		return activityDao.saves(activitieList);
	}
	@Override
	public List<Activity> getByClueId(String name,String clueId) {
		
		return activityDao.getByClueId(name,clueId);
	}
	@Override
	public List<Activity> getAll() {
		// TODO Auto-generated method stub
		return activityDao.getAll2();
	}

}
