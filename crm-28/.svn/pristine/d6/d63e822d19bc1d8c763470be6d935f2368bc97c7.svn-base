package com.bjpowernode.crm.settings.service.impl;

import java.util.List;

import com.bjpowernode.crm.settings.dao.DeptDao;
import com.bjpowernode.crm.settings.domain.Dept;
import com.bjpowernode.crm.settings.service.DeptService;
import com.bjpowernode.crm.utils.SqlSessionUtil;

public class DeptServiceImpl implements DeptService {

	private DeptDao deptDao = SqlSessionUtil.getCurrentSqlSession().getMapper(DeptDao.class);
	
	@Override
	public int save(Dept dept) {
		return deptDao.save(dept);
	}

	@Override
	public List<Dept> getAll() {
		return deptDao.getAll();
	}

	@Override
	public Dept getByCode(String code) {
		return deptDao.getByCode(code);
	}

	@Override
	public int update(Dept dept) {
		return deptDao.update(dept);
	}

	@Override
	public int deleteByCode(String[] codes) {
		return deptDao.deleteByCode(codes);
	}

}
