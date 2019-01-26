package com.bjpowernode.crm.workbench.dao;

import java.util.List;

import com.bjpowernode.crm.workbench.domain.ContactsActivityRelation;

public interface ContactsActivityRelationDao {

	/**
	 * 保存联系人和市场活动的关系
	 * @param carList
	 */
	void save(List<ContactsActivityRelation> carList);

}
