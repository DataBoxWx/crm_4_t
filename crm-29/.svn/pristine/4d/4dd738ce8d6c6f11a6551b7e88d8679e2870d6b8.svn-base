package com.bjpowernode.crm.settings.service.impl;

import java.util.List;

import com.bjpowernode.crm.settings.dao.DicTypeDao;
import com.bjpowernode.crm.settings.domain.DicType;
import com.bjpowernode.crm.settings.service.DicTypeService;
import com.bjpowernode.crm.utils.SqlSessionUtil;

public class DicTypeServiceImpl implements DicTypeService {

	private DicTypeDao dtd = SqlSessionUtil.getCurrentSqlSession().getMapper(DicTypeDao.class);
	
	@Override
	public DicType getByCode(String code) {
		return dtd.getByCode(code);
	}

	@Override
	public int save(DicType dt) {
		return dtd.save(dt);
	}

	@Override
	public List<DicType> getAll() {
		return dtd.getAll();
	}

}
