package com.bjpowernode.crm.workbench.service.impl;

import java.util.List;

import com.bjpowernode.crm.util.SqlSessionUtil;
import com.bjpowernode.crm.workbench.dao.ContactsRemarkDao;
import com.bjpowernode.crm.workbench.domain.ContactsRemark;
import com.bjpowernode.crm.workbench.service.ContactsRemarkService;

public class ContactsRemarkServiceImpl implements ContactsRemarkService {
	private ContactsRemarkDao contactsRemarkDao = SqlSessionUtil.getCurrentSqlSession().getMapper(ContactsRemarkDao.class);
	@Override
	public List<ContactsRemark> displayRemark(String contactsId) {
		// TODO Auto-generated method stub
		return contactsRemarkDao.displayRemark(contactsId);
	}
	@Override
	public Boolean saveRemark(ContactsRemark contactsRemark) {
		
		return contactsRemarkDao.saveRemark(contactsRemark) == 1;
	}
	@Override
	public Boolean update(ContactsRemark contactsRemark) {
		
		return contactsRemarkDao.update(contactsRemark) == 1;
	}
	@Override
	public Boolean deleteRemark(String id) {
		
		return contactsRemarkDao.delete(id)==1;
	}

}
