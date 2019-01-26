package com.bjpowernode.crm.settings.dao;

import com.bjpowernode.crm.settings.domain.User;

public interface UserDao {

	/**
	 * 
	 * @param user
	 * @return
	 */
	int save(User user);

}
