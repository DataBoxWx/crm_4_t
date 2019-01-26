package com.bjpowernode.crm.workbench.dao;

import java.util.List;
import java.util.Map;

import com.bjpowernode.crm.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Contacts;

public interface ContactsDao {
	/**
	 * 保存联系人
	 * @param contacts
	 * @return
	 */
	int saveContacts(Contacts contacts);
	
	/**
	 * 根据名字查联系人
	 * @param name
	 * @return
	 */
	List<Contacts> getContactsByName(String name);
	
	/**
	 * 根据客户id查询联系人列表
	 * @param customerId
	 * @return
	 */
	List<Contacts> getContactsByCustomerId(String customerId);
	
	/**
	 * 查总数
	 * @param selectMap
	 * @return
	 */
	Long getTotal(Map<String, Object> selectMap);
	
	/**
	 * 查列表
	 * @param selectMap
	 * @return
	 */
	List<Contacts> getAll(Map<String, Object> selectMap);
	
	/**
	 * 根据Id查询联系人
	 * @param id
	 * @return
	 */
	Contacts getById(String id);
	
	/**
	 * 
	 * @param contacts
	 * @return
	 */
	int update(Contacts contacts);
	
	/**
	 * 导出的列表
	 * @return
	 */
	List<Contacts> getAll2();
	
	/**
	 * 数组Id删除
	 * @param ids
	 * @return
	 */
	int delete(String[] ids);
	
	/**
	 * 导入保存
	 * @param contactsList
	 * @return
	 */
	int saves(List<Contacts> contactsList);
	
	/**
	 * 根据id获取contacts
	 * @param id
	 * @return
	 */
	Contacts getById2(String id);
	
	/**
	 * 
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
	int delete2(String id);

}
