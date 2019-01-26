package com.bjpowernode.crm.workbench.dao;

import java.util.List;

import com.bjpowernode.crm.workbench.domain.ContactsRemark;

public interface ContactsRemarkDao {

	int saves(List<ContactsRemark> carList);
	
	/**
	 * 备注列表
	 * @param contactsId
	 * @return
	 */
	List<ContactsRemark> displayRemark(String contactsId);
	
	/**
	 * 保存
	 * @param contactsRemark
	 * @return
	 */
	int saveRemark(ContactsRemark contactsRemark);
	
	/**
	 * 更新
	 * @param contactsRemark
	 * @return
	 */
	int update(ContactsRemark contactsRemark);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	int delete(String id);
	
	

	
	

}
