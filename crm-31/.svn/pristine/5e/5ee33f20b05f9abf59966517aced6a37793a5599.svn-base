package com.bjpowernode.crm.settings.dao;

import java.util.List;

import com.bjpowernode.crm.settings.domain.User;

public interface UserDao {

	/**
	 * 
	 * @param user
	 * @return
	 */
	int save(User user);

	/**
	 * 根据登录账号和密码获取用户
	 * @param loginAct
	 * @param loginPwd
	 * @return
	 */
	User getByLoginActAndLoginPwd(String loginAct, String loginPwd);

	/**
	 * 
	 * @return
	 */
	List<User> getAll();

}
