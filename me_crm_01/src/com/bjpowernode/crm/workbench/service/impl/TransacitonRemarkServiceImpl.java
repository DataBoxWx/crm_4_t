package com.bjpowernode.crm.workbench.service.impl;

import java.util.List;

import com.bjpowernode.crm.util.SqlSessionUtil;
import com.bjpowernode.crm.workbench.dao.TransactionRemarkDao;
import com.bjpowernode.crm.workbench.domain.TransactionRemark;
import com.bjpowernode.crm.workbench.service.TransacitonRemarkService;

public class TransacitonRemarkServiceImpl implements TransacitonRemarkService {
	private TransactionRemarkDao transactionRemarkDao = SqlSessionUtil.getCurrentSqlSession().getMapper(TransactionRemarkDao.class);
	@Override
	public int save(TransactionRemark transactionRemark) {
		
		return transactionRemarkDao.save(transactionRemark);
	}
	@Override
	public List<TransactionRemark> displayRemark() {
		
		return transactionRemarkDao.displayRemark();
	}

}
