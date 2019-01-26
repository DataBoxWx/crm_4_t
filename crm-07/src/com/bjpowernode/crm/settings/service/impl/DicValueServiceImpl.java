package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.settings.dao.DicValueDao;
import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.service.DicValueService;
import com.bjpowernode.crm.utils.SqlSessionUtil;

public class DicValueServiceImpl implements DicValueService {

	private DicValueDao dvd = SqlSessionUtil.getCurrentSqlSession().getMapper(DicValueDao.class);
	
	@Override
	public DicValue getByTypeCodeAndValue(String typeCode, String value) {
		return dvd.getByTypeCodeAndValue(typeCode , value);
	}

	@Override
	public int save(DicValue dv) {
		return dvd.save(dv);
	}

}
