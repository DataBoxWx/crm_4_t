package com.bjpowernode.crm.workbench.service;

import java.util.List;

import com.bjpowernode.crm.workbench.domain.ContactsRemark;

public interface ContactsRemarkService {
	
	/**
	 * 备注列表
	 * @param contactsId
	 * @return
	 */
	List<ContactsRemark> displayRemark(String contactsId);
	
	/**
	 * 保存备注
	 * @param contactsRemark
	 * @return
	 */
	Boolean saveRemark(ContactsRemark contactsRemark);
	
	/**
	 * 更新
	 * @param contactsRemark
	 * @return
	 */
	Boolean update(ContactsRemark contactsRemark);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	Boolean deleteRemark(String id);

}
