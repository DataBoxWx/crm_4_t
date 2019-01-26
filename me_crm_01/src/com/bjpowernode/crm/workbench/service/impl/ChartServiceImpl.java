package com.bjpowernode.crm.workbench.service.impl;

import java.util.List;
import java.util.Map;

import com.bjpowernode.crm.util.SqlSessionUtil;
import com.bjpowernode.crm.workbench.dao.ChartDao;
import com.bjpowernode.crm.workbench.dao.TransactionDao;
import com.bjpowernode.crm.workbench.service.ChartService;

public class ChartServiceImpl implements ChartService {
	private ChartDao charDao = SqlSessionUtil.getCurrentSqlSession().getMapper(ChartDao.class);
	private TransactionDao transactionDao = SqlSessionUtil.getCurrentSqlSession().getMapper(TransactionDao.class);
	@Override
	public List<Map<String, Object>> getCountByStage() {
		
		return transactionDao.getCountByStage();
	}

}
