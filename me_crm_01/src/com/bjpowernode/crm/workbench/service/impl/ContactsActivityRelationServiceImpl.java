package com.bjpowernode.crm.workbench.service.impl;

import java.util.List;

import com.bjpowernode.crm.util.SqlSessionUtil;
import com.bjpowernode.crm.workbench.dao.ContactsActivityRelationDao;
import com.bjpowernode.crm.workbench.domain.ContactsActivityRelation;
import com.bjpowernode.crm.workbench.service.ContactsActivityRelationService;

public class ContactsActivityRelationServiceImpl implements ContactsActivityRelationService {
	private ContactsActivityRelationDao carDao = SqlSessionUtil.getCurrentSqlSession().getMapper(ContactsActivityRelationDao.class);
	@Override
	public Boolean bundRelarion(List<ContactsActivityRelation> list) {
		
		return carDao.saves(list) > 0;
	}
	@Override
	public Boolean unbund(String activityId, String contactsId) {
		
		return carDao.unband(activityId,contactsId);
	}

}
