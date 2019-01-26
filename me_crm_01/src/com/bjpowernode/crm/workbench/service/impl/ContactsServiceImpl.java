package com.bjpowernode.crm.workbench.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bjpowernode.crm.dao.UserDao;
import com.bjpowernode.crm.domain.Activity;
import com.bjpowernode.crm.domain.PaginationVo;
import com.bjpowernode.crm.domain.User;
import com.bjpowernode.crm.util.Const;
import com.bjpowernode.crm.util.DateUtil;
import com.bjpowernode.crm.util.SqlSessionUtil;
import com.bjpowernode.crm.util.UUIDGenerator;
import com.bjpowernode.crm.workbench.dao.ContactsDao;
import com.bjpowernode.crm.workbench.dao.CustomerDao;
import com.bjpowernode.crm.workbench.dao.TransactionDao;
import com.bjpowernode.crm.workbench.domain.Contacts;
import com.bjpowernode.crm.workbench.domain.Customer;
import com.bjpowernode.crm.workbench.domain.Transaction;
import com.bjpowernode.crm.workbench.service.ContactsService;

public class ContactsServiceImpl implements ContactsService {
	private ContactsDao contactsDao = SqlSessionUtil.getCurrentSqlSession().getMapper(ContactsDao.class);
	private CustomerDao customerDao = SqlSessionUtil.getCurrentSqlSession().getMapper(CustomerDao.class);
	private UserDao userDao = SqlSessionUtil.getCurrentSqlSession().getMapper(UserDao.class);
	private TransactionDao transactionDao = SqlSessionUtil.getCurrentSqlSession().getMapper(TransactionDao.class);
	@Override
	public List<Contacts> getContactsByName(String name) {
		
		return contactsDao.getContactsByName(name);
	}
	@Override
	public List<Contacts> getContactsByCustomerId(String customerId) {
		
		return contactsDao.getContactsByCustomerId(customerId);
	}
	@Override
	public PaginationVo<Contacts> doDisplayContacts(Map<String, Object> selectMap) {
		PaginationVo<Contacts> vo = new PaginationVo<>();
		vo.setTotal(contactsDao.getTotal(selectMap));
		vo.setDataList(contactsDao.getAll(selectMap));
		return vo;
	}
	@Override
	public List<String> getCustomerNameByName(String name) {
		
		return customerDao.getByName(name);
	}
	@Override
	public Boolean save(Contacts contacts,String customerName) {
		Customer customer = customerDao.getByName2(customerName);
		if(customer == null){
			customer = new Customer();
			customer.setId(UUIDGenerator.generate());
			customer.setOwner(contacts.getOwner());
			customer.setName(customerName);
			customer.setWebsite(contacts.getEmail());
			customer.setDescription(contacts.getDescription());
			customer.setContactSummary(contacts.getContactSummary());
			customer.setRelationTimeNext(contacts.getRelationTimeNext());
			customer.setAddress(contacts.getAddress());
			customer.setCreatBy(contacts.getCreatBy());
			customer.setCreatTime(DateUtil.getSysTime());
			customerDao.saveCustomer(customer);
		}
		contacts.setCustomerId(customer.getId());
		return contactsDao.saveContacts(contacts) == 1;
	}
	@Override
	public Map<String, Object> getById(String id) {
		Map<String, Object> map = new HashMap<>();
		map.put("userList", userDao.list());
		map.put("contacts", contactsDao.getById(id));
		return map;
	}
	@Override
	public Boolean update(Contacts contacts, String customerName) {
		Customer customer = customerDao.getByName2(customerName);
		if(customer == null){
			customer = new Customer();
			customer.setId(UUIDGenerator.generate());
			customer.setOwner(contacts.getOwner());
			customer.setName(customerName);
			customer.setWebsite(contacts.getEmail());
			customer.setDescription(contacts.getDescription());
			customer.setContactSummary(contacts.getContactSummary());
			customer.setRelationTimeNext(contacts.getRelationTimeNext());
			customer.setAddress(contacts.getAddress());
			customer.setCreatBy(contacts.getCreatBy());
			customer.setCreatTime(DateUtil.getSysTime());
			customerDao.saveCustomer(customer);
		}
		contacts.setCustomerId(customer.getId());
		return contactsDao.update(contacts) == 1;
	}
	@Override
	public List<Contacts> getAll() {
		
		return contactsDao.getAll2();
	}
	@Override
	public Boolean delete(String[] ids) {
		
		return contactsDao.delete(ids) > 0;
	}
	@Override
	public int saves(List<Contacts> contactsList) {
		
		return contactsDao.saves(contactsList);
	}
	@Override
	public Contacts getById2(String id) {
		
		return contactsDao.getById2(id);
	}
	@Override
	public List<Transaction> getByContactsId(String contactsId) {
		
		return transactionDao.getByContactsId(contactsId);
	}
	@Override
	public List<Activity> getActivityByContactsId(String contactsId) {
		
		return contactsDao.getActivityByContactsId(contactsId);
	}
	@Override
	public List<Activity> getActivityById(String contactsId,String name,String pingyin) {
		// TODO Auto-generated method stub
		return contactsDao.getActivityById(contactsId,name,pingyin);
	}
	@Override
	public Boolean delete2(String id) {
		
		return contactsDao.delete2(id) == 1;
	}

}
