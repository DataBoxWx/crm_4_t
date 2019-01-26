package com.bjpowernode.crm.workbench.service.impl;

import java.util.Map;

import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.vo.PaginationVO;
import com.bjpowernode.crm.workbench.dao.ActivityDao;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.service.ActivityService;

public class ActivityServiceImpl implements ActivityService {

	private ActivityDao ad = SqlSessionUtil.getCurrentSqlSession().getMapper(ActivityDao.class);

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

}
