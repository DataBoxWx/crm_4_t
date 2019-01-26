package com.bjpowernode.crm.workbench.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.bjpowernode.crm.utils.DateUtil;
import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.utils.UUIDGenerator;
import com.bjpowernode.crm.workbench.dao.ClueActivityRelationDao;
import com.bjpowernode.crm.workbench.dao.ClueDao;
import com.bjpowernode.crm.workbench.dao.ClueRemarkDao;
import com.bjpowernode.crm.workbench.dao.ContactsActivityRelationDao;
import com.bjpowernode.crm.workbench.dao.ContactsDao;
import com.bjpowernode.crm.workbench.dao.ContactsRemarkDao;
import com.bjpowernode.crm.workbench.dao.CustomerDao;
import com.bjpowernode.crm.workbench.dao.CustomerRemarkDao;
import com.bjpowernode.crm.workbench.dao.TranDao;
import com.bjpowernode.crm.workbench.dao.TranRemarkDao;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.ClueRemark;
import com.bjpowernode.crm.workbench.domain.Contacts;
import com.bjpowernode.crm.workbench.domain.ContactsActivityRelation;
import com.bjpowernode.crm.workbench.domain.ContactsRemark;
import com.bjpowernode.crm.workbench.domain.Customer;
import com.bjpowernode.crm.workbench.domain.CustomerRemark;
import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.domain.TranRemark;
import com.bjpowernode.crm.workbench.service.ClueService;

public class ClueServiceImpl implements ClueService {

	private ClueDao clueDao = SqlSessionUtil.getCurrentSqlSession().getMapper(ClueDao.class);
	private CustomerDao customerDao = SqlSessionUtil.getCurrentSqlSession().getMapper(CustomerDao.class);
	private ContactsDao contactsDao = SqlSessionUtil.getCurrentSqlSession().getMapper(ContactsDao.class);
	private ClueActivityRelationDao clueActivityRelationDao = SqlSessionUtil.getCurrentSqlSession().getMapper(ClueActivityRelationDao.class);
	private ContactsActivityRelationDao contactsActivityRelationDao = SqlSessionUtil.getCurrentSqlSession().getMapper(ContactsActivityRelationDao.class);
	private ClueRemarkDao clueRemarkDao = SqlSessionUtil.getCurrentSqlSession().getMapper(ClueRemarkDao.class);
	private CustomerRemarkDao customerRemarkDao = SqlSessionUtil.getCurrentSqlSession().getMapper(CustomerRemarkDao.class);
	private ContactsRemarkDao contactsRemarkDao = SqlSessionUtil.getCurrentSqlSession().getMapper(ContactsRemarkDao.class);
	private TranDao tranDao = SqlSessionUtil.getCurrentSqlSession().getMapper(TranDao.class);
	private TranRemarkDao tranRemarkDao = SqlSessionUtil.getCurrentSqlSession().getMapper(TranRemarkDao.class);
	
	@Override
	public boolean save(Clue clue) {
		return clueDao.save(clue) == 1;
	}

	@Override
	public Clue getById(String id) {
		return clueDao.getById(id);
	}

	@Override
	public void convert(String clueId , String operator ,Tran tran) {
//		1、根据线索id获取线索信息
		Clue clue = clueDao.getById2(clueId);
//		2、将线索信息中客户的信息提取出来，放到客户表当中（客户不可重复，按照公司的名称判断客户是否已存在，精确匹配）
		Customer customer = customerDao.getByName(clue.getCompany());
		if(customer == null){
			customer = new Customer();
			customer.setId(UUIDGenerator.generate());
			customer.setOwner(clue.getOwner());
			customer.setName(clue.getCompany());
			customer.setWebsite(clue.getWebsite());
			customer.setPhone(clue.getPhone());
			customer.setDescription(clue.getDescription());
			customer.setContactSummary(clue.getContactSummary());
			customer.setNextContactTime(clue.getNextContactTime());
			customer.setAddress(clue.getAddress());
			customer.setCreateTime(DateUtil.getSysTime());
			customer.setCreateBy(operator);
			customerDao.save(customer);
		}
//		3、将线索信息中联系人信息提取出来，放到联系人表当中
		Contacts contacts = new Contacts();
		contacts.setId(UUIDGenerator.generate());
		contacts.setOwner(clue.getOwner());
		contacts.setSource(clue.getSource());
		contacts.setName(clue.getFullname());
		contacts.setAppellation(clue.getAppellation());
		contacts.setJob(clue.getJob());
		contacts.setMphone(clue.getMphone());
		contacts.setEmail(clue.getEmail());
		contacts.setCustomerId(customer.getId());
		contacts.setDescription(clue.getDescription());
		contacts.setContactSummary(clue.getContactSummary());
		contacts.setNextContactTime(clue.getNextContactTime());
		contacts.setAddress(clue.getAddress());
		contacts.setCreateTime(DateUtil.getSysTime());
		contacts.setCreateBy(operator);
		contactsDao.save(contacts);

//		4、将“线索和市场活动的关系”转换到“联系人和市场活动的关系”表当中
		List<String> activityIds = clueActivityRelationDao.getActivityIdByClueId(clueId);
		if(activityIds.size() > 0){
			List<ContactsActivityRelation> carList = new ArrayList<>();
			for(String activityId : activityIds){
				ContactsActivityRelation car = new ContactsActivityRelation();
				car.setId(UUIDGenerator.generate());
				car.setContactsId(contacts.getId());
				car.setActivityId(activityId);
				carList.add(car);
			}
			contactsActivityRelationDao.save(carList);
		}
//		5、线索备注转换到：联系人备注、客户备注当中
		List<ClueRemark> clueRemarkList = clueRemarkDao.getByClueId(clueId);
		
		if(clueRemarkList.size() > 0){
			
			List<CustomerRemark> customerRemarkList = new ArrayList<>();
			List<ContactsRemark> contactsRemarkList = new ArrayList<>();
			
			for(ClueRemark clueRemark : clueRemarkList){
				
				CustomerRemark customerRemark = new CustomerRemark();
				customerRemark.setId(UUIDGenerator.generate());
				customerRemark.setNoteContent(clueRemark.getNoteContent());
				customerRemark.setCreateTime(clueRemark.getCreateTime());
				customerRemark.setCreateBy(clueRemark.getCreateBy());
				customerRemark.setEditTime(clueRemark.getEditTime());
				customerRemark.setEditBy(clueRemark.getEditBy());
				customerRemark.setEditFlag(clueRemark.getEditFlag());
				customerRemark.setCustomerId(customer.getId());
				
				customerRemarkList.add(customerRemark);
				
				ContactsRemark contactsRemark = new ContactsRemark();
				contactsRemark.setId(UUIDGenerator.generate());
				contactsRemark.setNoteContent(clueRemark.getNoteContent());
				contactsRemark.setCreateTime(clueRemark.getCreateTime());
				contactsRemark.setCreateBy(clueRemark.getCreateBy());
				contactsRemark.setEditTime(clueRemark.getEditTime());
				contactsRemark.setEditBy(clueRemark.getEditBy());
				contactsRemark.setEditFlag(clueRemark.getEditFlag());
				contactsRemark.setContactsId(contacts.getId());
				
				contactsRemarkList.add(contactsRemark);
			}
			
			customerRemarkDao.save(customerRemarkList);
			contactsRemarkDao.save(contactsRemarkList);
		}
		
//		6、判断用户是否在线索转换的同时创建交易，假设用户选择了创建交易，则：
		if(tran != null){
			//保存交易
			tran.setOwner(clue.getOwner());
			tran.setCustomerId(customer.getId());
			tran.setSource(clue.getSource());
			tran.setContactsId(contacts.getId());
			tran.setCreateBy(operator);
			tranDao.save(tran);
			//保存交易备注
			if(clueRemarkList.size() > 0){
				List<TranRemark> tranRemarkList = new ArrayList<>();
				for(ClueRemark clueRemark : clueRemarkList){
					TranRemark tranRemark = new TranRemark();
					tranRemark.setId(UUIDGenerator.generate());
					tranRemark.setNoteContent(clueRemark.getNoteContent());
					tranRemark.setCreateTime(clueRemark.getCreateTime());
					tranRemark.setCreateBy(clueRemark.getCreateBy());
					tranRemark.setEditTime(clueRemark.getEditTime());
					tranRemark.setEditBy(clueRemark.getEditBy());
					tranRemark.setEditFlag(clueRemark.getEditFlag());
					tranRemark.setTranId(tran.getId());
					tranRemarkList.add(tranRemark);
				}
				tranRemarkDao.save(tranRemarkList);
			}
		}
		
//		7、删除线索备注
		clueRemarkDao.deleteByClueId(clueId);
		
//		8、删除线索和市场活动的关系
		clueActivityRelationDao.deleteByClueId(clueId);
		
//		9、删除线索
		clueDao.deleteById(clueId);
		
	}

}

























