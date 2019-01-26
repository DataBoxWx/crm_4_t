package com.bjpowernode.crm.workbench.service;

import java.util.List;
import java.util.Map;

import com.bjpowernode.crm.domain.Activity;
import com.bjpowernode.crm.domain.PaginationVo;
import com.bjpowernode.crm.workbench.domain.Contacts;
import com.bjpowernode.crm.workbench.domain.Transaction;

public interface ContactsService {
	
	/**
	 * 根据名字模糊查询列表
	 * @param name
	 * @return
	 */
	List<Contacts> getContactsByName(String name);
	
	/**
	 * g根据客户id查询联系人列表
	 * @param customerId
	 * @return
	 */
	List<Contacts> getContactsByCustomerId(String customerId);
	
	/**
	 * 分页查询
	 * @param selectMap
	 * @return
	 */
	PaginationVo<Contacts> doDisplayContacts(Map<String, Object> selectMap);
	
	/**
	 * 根据客户名字得到客户列表
	 * @return
	 */
	List<String> getCustomerNameByName(String name);
	
	/**
	 * 保存
	 * @param contacts
	 * @return
	 */
	Boolean save(Contacts contacts,String customerName);
	
	/**
	 * 根据Id获取联系人及用户列表
	 * @param id
	 * @return
	 */
	Map<String, Object> getById(String id);
	
	/**
	 * 根据id更新
	 * @param contacts
	 * @param customerName
	 * @return
	 */
	Boolean update(Contacts contacts, String customerName);
	
	/**
	 * 导出
	 * @return
	 */
	List<Contacts> getAll();
	
	/**
	 * 根据id数组删除
	 * @param ids
	 * @return
	 */
	Boolean delete(String[] ids);
	
	/**
	 * 导入保存
	 * @param contactsList
	 * @return
	 */
	int saves(List<Contacts> contactsList);
	
	/**
	 * 根据id获取联系人
	 * @param id
	 * @return
	 */
	Contacts getById2(String id);
	
	/**
	 * 得到交易列表
	 * @param contactsId
	 * @return
	 */
	List<Transaction> getByContactsId(String contactsId);
	
	/**
	 * 根据关系表获取活动列表
	 * @param contactsId
	 * @return
	 */
	List<Activity> getActivityByContactsId(String contactsId);
	
	/**
	 * 
	 * @param contactsId
	 * @return
	 */
	List<Activity> getActivityById(String contactsId,String name,String pingyin);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	Boolean delete2(String id);

}
