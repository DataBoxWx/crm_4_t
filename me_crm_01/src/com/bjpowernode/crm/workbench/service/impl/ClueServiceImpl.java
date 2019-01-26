package com.bjpowernode.crm.workbench.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bjpowernode.crm.dao.UserDao;
import com.bjpowernode.crm.util.SqlSessionUtil;
import com.bjpowernode.crm.util.UUIDGenerator;
import com.bjpowernode.crm.workbench.dao.ActivityDao;
import com.bjpowernode.crm.workbench.dao.ClueActivityRelationDao;
import com.bjpowernode.crm.workbench.dao.ClueDao;
import com.bjpowernode.crm.workbench.dao.ClueRemarkDao;
import com.bjpowernode.crm.workbench.dao.ContactsActivityRelationDao;
import com.bjpowernode.crm.workbench.dao.ContactsDao;
import com.bjpowernode.crm.workbench.dao.ContactsRemarkDao;
import com.bjpowernode.crm.workbench.dao.CustomerDao;
import com.bjpowernode.crm.workbench.dao.CustomerRemarkDao;
import com.bjpowernode.crm.workbench.dao.TransactionDao;
import com.bjpowernode.crm.workbench.dao.TransactionRemarkDao;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.ClueRemark;
import com.bjpowernode.crm.workbench.domain.Contacts;
import com.bjpowernode.crm.workbench.domain.ContactsActivityRelation;
import com.bjpowernode.crm.workbench.domain.ContactsRemark;
import com.bjpowernode.crm.workbench.domain.Customer;
import com.bjpowernode.crm.workbench.domain.CustomerRemark;
import com.bjpowernode.crm.workbench.domain.Transaction;
import com.bjpowernode.crm.workbench.domain.TransactionRemark;
import com.bjpowernode.crm.workbench.service.ClueService;

public class ClueServiceImpl implements ClueService {
	private ClueDao clueDao = SqlSessionUtil.getCurrentSqlSession().getMapper(ClueDao.class);
	private ClueRemarkDao clueRemarkDao = SqlSessionUtil.getCurrentSqlSession().getMapper(ClueRemarkDao.class);
	private UserDao userDao = SqlSessionUtil.getCurrentSqlSession().getMapper(UserDao.class);
	private ClueActivityRelationDao clueActivityRelationDao = SqlSessionUtil.getCurrentSqlSession().getMapper(ClueActivityRelationDao.class);
	private ActivityDao activityDao = SqlSessionUtil.getCurrentSqlSession().getMapper(ActivityDao.class);
	private ContactsDao contactsDao = SqlSessionUtil.getCurrentSqlSession().getMapper(ContactsDao.class);
	private CustomerDao customerDao = SqlSessionUtil.getCurrentSqlSession().getMapper(CustomerDao.class);
	private ContactsActivityRelationDao contactsActivityRelationDao = SqlSessionUtil.getCurrentSqlSession().getMapper(ContactsActivityRelationDao.class);
	private ContactsRemarkDao contactsRemarkDao = SqlSessionUtil.getCurrentSqlSession().getMapper(ContactsRemarkDao.class);
	private CustomerRemarkDao customerRemarkDao = SqlSessionUtil.getCurrentSqlSession().getMapper(CustomerRemarkDao.class);
	private TransactionDao transactionDao = SqlSessionUtil.getCurrentSqlSession().getMapper(TransactionDao.class);
	private TransactionRemarkDao transactionRemarkDao = SqlSessionUtil.getCurrentSqlSession().getMapper(TransactionRemarkDao.class);
	@Override
	public Map<String, Object> getOwnerClueResource() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user", userDao.list());
		map.put("activity", activityDao.getAll());
		return map;
	}
	@Override
	public int saveClue(Clue clue) {

		return clueDao.saveClue(clue);
	}
	@Override
	public Map<String, Object> display(Map<String, Object> map) {
		List<Clue> list = clueDao.display(map);
		int total = clueDao.getTotal(map);
		Map<String, Object> map2 = new HashMap<>();
		map2.put("list", list);
		map2.put("total", total);
		return map2;
	}
	@Override
	public Clue getClueById(String id) {
		
		return clueDao.getClueById(id);
	}
	@Override
	public List<Clue> getAllClue() {
		
		return clueDao.getAllClue();
	}
	@Override
	public Boolean getClueById2(String clueId,Transaction transaction,String operator) {
//		1、根据线索id获取线索信息
		Clue clue = clueDao.getClueById3(clueId);
//		2、将线索信息中客户的信息提取出来，放到客户表当中（客户不可重复，按照公司的名称判断客户是否已存在，精确匹配）
		
		Customer customer = customerDao.getCustomer(clue.getCompany());
		int count = 0;
		if(customer == null){
			customer = new Customer();
			customer.setId(UUIDGenerator.generate());
			customer.setOwner(clue.getOwner());
			customer.setName(clue.getCompany());
			customer.setWebsite(clue.getWeb());
			customer.setTelephone(clue.getTelephone());
			customer.setDescription(clue.getClueDesc());
			customer.setContactSummary(clue.getRelationSummary());
			customer.setRelationTimeNext(clue.getRelationTimeNext());
			customer.setAddress(clue.getAddress());
			customer.setCreatBy(clue.getCreatBy());
			customer.setCreatTime(clue.getCreatTime());
			count += customerDao.saveCustomer(customer);
		}
//		3、将线索信息中联系人信息提取出来，放到联系人表当中
		Contacts contacts = new Contacts();
		contacts.setId(UUIDGenerator.generate());
		contacts.setOwner(clue.getOwner());
		contacts.setSource(clue.getClueFrom());
		contacts.setName(clue.getName());
		contacts.setAppellation(clue.getAppellation());
		contacts.setPosition(clue.getPosition());
		contacts.setPhone(clue.getPhone());
		contacts.setEmail(clue.getEmail());
		contacts.setCustomerId(customer.getId());
		contacts.setDescription(clue.getClueDesc());
		contacts.setContactSummary(clue.getRelationSummary());
		contacts.setRelationTimeNext(clue.getRelationTimeNext());
		contacts.setAddress(clue.getAddress());
		contacts.setCreatTime(clue.getCreatTime());
		contacts.setCreatBy(clue.getCreatBy());
		count += contactsDao.saveContacts(contacts);

//		4、将“线索和市场活动的关系”转换到“联系人和市场活动的关系”表当中
		List<String> allByClueId = clueActivityRelationDao.getActivityIdByClueId(clue.getId());
		if(allByClueId.size() > 0){
			List<ContactsActivityRelation> carList = new ArrayList<>();
			for (String activityId : allByClueId) {
				ContactsActivityRelation car = new ContactsActivityRelation();
				car.setId(UUIDGenerator.generate());
				car.setContactsId(contacts.getId());
				car.setActivityId(activityId);
				carList.add(car);
			}
			 contactsActivityRelationDao.saves(carList);
			
		}
		
//		5、线索备注转换到：联系人备注、客户备注当中
		List<ClueRemark> displayRemark = clueRemarkDao.displayRemark();
		if(displayRemark.size() > 0){
			List<ContactsRemark> carList = new ArrayList<>();
			List<CustomerRemark> customerRemarkList = new ArrayList<>();
			for (ClueRemark clueRemark : displayRemark) {
				ContactsRemark cr = new ContactsRemark();
				cr.setId(UUIDGenerator.generate());
				cr.setNoteContent(clueRemark.getNoteContent());
				cr.setCreatBy(clueRemark.getCreatBy());
				cr.setCreatTime(clueRemark.getCreatTime());
				cr.setEditBy(clueRemark.getEditBy());
				cr.setEditTime(clueRemark.getEditTime());
				cr.setEditFlag(clueRemark.getEditFlag());
				cr.setContactId(contacts.getId());
				carList.add(cr);
				
				CustomerRemark customerRemark = new CustomerRemark();
				customerRemark.setId(UUIDGenerator.generate());
				customerRemark.setNoteContent(clueRemark.getNoteContent());
				customerRemark.setEditFlag(clueRemark.getEditFlag());
				customerRemark.setCreatBy(clueRemark.getCreatBy());
				customerRemark.setCreatTime(clueRemark.getCreatTime());
				customerRemark.setEditBy(clueRemark.getEditBy());
				customerRemark.setEditTime(clueRemark.getEditTime());
				customerRemark.setCustomerId(customer.getId());
				customerRemarkList.add(customerRemark);
			}
			count += contactsRemarkDao.saves(carList);
			count += customerRemarkDao.saves(customerRemarkList);
		}

//		6、判断用户是否在线索转换的同时创建交易，假设用户选择了创建交易，则：
//			将交易信息保存到交易表

		if(transaction != null){
			transaction.setOwner(clue.getOwner());
			transaction.setCustomerId(customer.getId());
			transaction.setSource(clue.getClueFrom());
			transaction.setContactsId(contacts.getId());
			transaction.setDescription(clue.getClueDesc());
			transaction.setContactSummary(clue.getRelationSummary());
			transaction.setRelationTimeNext(clue.getRelationTimeNext());
			transaction.setCreatBy(operator);
			count += transactionDao.save1(transaction);
//			将线索备注转换到交易备注
			if(displayRemark.size() > 0 ){
				List<TransactionRemark> trList = new ArrayList<>();
				for (ClueRemark clueRemark : displayRemark) {
					TransactionRemark tr = new TransactionRemark();
					tr.setId(UUIDGenerator.generate());
					tr.setNoteContent(clueRemark.getNoteContent());
					tr.setEditFlag(clueRemark.getEditFlag());
					tr.setCreatBy(clueRemark.getCreatBy());
					tr.setCreatTime(clueRemark.getCreatTime());
					tr.setEditBy(clueRemark.getEditBy());
					tr.setEditTime(clueRemark.getEditTime());
					tr.setTransactionId(transaction.getId());;
					trList.add(tr);
				}
				count += transactionRemarkDao.save1(trList);
			}
		}
		
//		7、删除线索备注
		count += clueRemarkDao.deleteRemarkByClueId(clueId);
//		8、删除线索和市场活动的关系
		count += clueActivityRelationDao.deleteByClueId(clueId);
//		9、删除线索
		count += clueDao.deleteClueById2(clueId);
		return count > 0 ? true : false;
	}
	@Override
	public Map<String, Object> getClueByIdAndUser(String id) {
		Map<String , Object> map = new HashMap<>();
		map.put("user", userDao.list());
		map.put("clue", clueDao.getClueById3(id));
		return map;
	}
	@Override
	public int updateClue(Clue clue) {
		
		return clueDao.updateClueById(clue);
	}
	@Override
	public int deleteClueById(String[] ids) {
		
		return clueDao.deleteClueById(ids);
	}
	@Override
	public int saves(List<Clue> clueList) {
		
		return clueDao.saves(clueList);
		
	}
	@Override
	public int DeleteClueAndRemarkAndRelationById(String id) {
		
		return clueDao.deleteClueById2(id) + clueRemarkDao.deleteRemarkByClueId(id) +clueActivityRelationDao.deleteByClueId(id);
	}

}
