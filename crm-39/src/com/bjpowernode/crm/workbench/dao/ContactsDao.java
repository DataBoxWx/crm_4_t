package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Contacts;

public interface ContactsDao {

	/**
	 * 保存联系人
	 * @param contacts
	 */
	void save(Contacts contacts);

}
