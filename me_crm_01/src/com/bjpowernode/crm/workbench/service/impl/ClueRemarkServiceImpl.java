package com.bjpowernode.crm.workbench.service.impl;

import java.util.List;

import com.bjpowernode.crm.util.SqlSessionUtil;
import com.bjpowernode.crm.workbench.dao.ClueRemarkDao;
import com.bjpowernode.crm.workbench.domain.ClueRemark;
import com.bjpowernode.crm.workbench.service.ClueRemarkService;

public class ClueRemarkServiceImpl implements ClueRemarkService {
	private ClueRemarkDao clueRemarkDao = SqlSessionUtil.getCurrentSqlSession().getMapper(ClueRemarkDao.class);
	@Override
	public int saveRemark(ClueRemark clueRemark) {

		return clueRemarkDao.saveRemark(clueRemark);
	}
	@Override
	public List<ClueRemark> displayRemark() {
		
		return clueRemarkDao.displayRemark();
	}
	@Override
	public int updateRemarkById(ClueRemark clueRemark) {

		return clueRemarkDao.updateRemarkById(clueRemark);
	}
	@Override
	public int deleteRemarkById(String id) {

		return clueRemarkDao.deleteRemarkById(id);
	}

	

}
