package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.settings.domain.User;

public interface UserService {

	/**
	 * 保存用户
	 * @param user
	 * @return
	 */
	int save(User user);

	/**
	 * 用户登录
	 * @param loginAct
	 * @param loginPwd
	 * @param clientIp
	 * @return
	 */
	User login(String loginAct, String loginPwd, String clientIp);

}
