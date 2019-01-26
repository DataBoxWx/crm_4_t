package com.bjpowernode.crm.workbench.service;

import java.util.List;

import com.bjpowernode.crm.workbench.domain.ContactsActivityRelation;

public interface ContactsActivityRelationService {
	
	//绑定活动列表
	Boolean bundRelarion(List<ContactsActivityRelation> list);
	
	/**
	 * 解除关系
	 * @param activityId
	 * @param contactsId
	 * @return
	 */
	Boolean unbund(String activityId, String contactsId);

}
