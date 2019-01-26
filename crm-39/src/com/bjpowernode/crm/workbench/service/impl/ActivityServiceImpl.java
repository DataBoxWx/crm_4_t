package com.bjpowernode.crm.workbench.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.vo.PaginationVO;
import com.bjpowernode.crm.workbench.dao.ActivityDao;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.service.ActivityService;

public class ActivityServiceImpl implements ActivityService {

	private ActivityDao ad = SqlSessionUtil.getCurrentSqlSession().getMapper(ActivityDao.class);
	private UserDao userDao = SqlSessionUtil.getCurrentSqlSession().getMapper(UserDao.class);

	@Override
	public boolean save(Activity activity) {
		return ad.save(activity) == 1;
	}

	@Override
	public PaginationVO<Activity> getPageByCondition(Map<String, Object> conditionMap) {
		PaginationVO<Activity> page = new PaginationVO<>();
		page.setTotal(ad.getTotalByCondition(conditionMap));
		page.setDataList(ad.getDataListByCondition(conditionMap));
		return page;
	}

	@Override
	public Map<String, Object> getById(String id) {
		Map<String,Object> activityMap = new HashMap<>();
		activityMap.put("userList", userDao.getAll());
		activityMap.put("activity", ad.getById(id));
		return activityMap;
	}

	@Override
	public boolean update(Activity a) {
		return ad.update(a) == 1;
	}

	@Override
	public boolean deleteById(String[] ids) {
		return ad.deleteById(ids) == ids.length;
	}

	@Override
	public Activity getById2(String id) {
		return ad.getById2(id);
	}

	@Override
	public List<Activity> getAll() {
		return ad.getAll();
	}

	@Override
	public List<Activity> getById3(String[] ids) {
		return ad.getById3(ids);
	}

	@Override
	public boolean save2(List<Activity> activityList) {
		return ad.save2(activityList) == activityList.size();
	}

	@Override
	public List<Activity> getByNameAndClueId(String activityName, String clueId) {
		return ad.getByNameAndClueId(activityName,clueId);
	}

}









