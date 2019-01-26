package com.bjpowernode.crm.workbench.service.impl;

import java.util.List;

import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.workbench.dao.TranHistoryDao;
import com.bjpowernode.crm.workbench.domain.TranHistory;
import com.bjpowernode.crm.workbench.service.TranHistoryService;

public class TranHistoryServiceImpl implements TranHistoryService {

	private TranHistoryDao thd = SqlSessionUtil.getCurrentSqlSession().getMapper(TranHistoryDao.class);
	
	@Override
	public List<TranHistory> getByTranId(String tranId) {
		return thd.getByTranId(tranId);
	}

}
