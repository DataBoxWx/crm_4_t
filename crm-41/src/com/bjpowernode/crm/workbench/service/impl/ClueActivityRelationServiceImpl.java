package com.bjpowernode.crm.workbench.service.impl;

import java.util.List;

import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.workbench.dao.ClueActivityRelationDao;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;
import com.bjpowernode.crm.workbench.service.ClueActivityRelationService;

public class ClueActivityRelationServiceImpl implements ClueActivityRelationService {

	private ClueActivityRelationDao card = SqlSessionUtil.getCurrentSqlSession().getMapper(ClueActivityRelationDao.class);
	
	@Override
	public List<Activity> getActivityByClueId(String clueId) {
		return card.getActivityByClueId(clueId);
	}

	@Override
	public boolean deleteById(String relationId) {
		return card.deleteById(relationId) == 1;
	}

	@Override
	public boolean save(List<ClueActivityRelation> carList) {
		return card.save(carList) == carList.size();
	}

}
