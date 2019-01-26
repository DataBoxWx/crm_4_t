package com.bjpowernode.crm.workbench.service.impl;

import java.util.List;

import com.bjpowernode.crm.domain.Activity;
import com.bjpowernode.crm.util.SqlSessionUtil;
import com.bjpowernode.crm.workbench.dao.ClueActivityRelationDao;
import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;
import com.bjpowernode.crm.workbench.service.ClueActivityRelationService;

public class ClueActivityRelationServiceImpl implements ClueActivityRelationService {
	private ClueActivityRelationDao clueActivityRelationDao = SqlSessionUtil.getCurrentSqlSession().getMapper(ClueActivityRelationDao.class);
	@Override
	public List<Activity> getAllByClueId(String clueId) {
		// TODO Auto-generated method stub
		return clueActivityRelationDao.getAllByClueId(clueId);
	}
	@Override
	public int unbundRelation(String id) {
		// TODO Auto-generated method stub
		return clueActivityRelationDao.unbundRelation(id);
	}
	@Override
	public int saveByActivityIdAndClueId(List<ClueActivityRelation> list) {
		
		return clueActivityRelationDao.saveByActivityIdAndClueId(list);
	}

}
