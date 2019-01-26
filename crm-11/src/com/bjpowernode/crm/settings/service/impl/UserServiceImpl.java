package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.utils.SqlSessionUtil;

public class UserServiceImpl implements UserService {

	private UserDao userDao = SqlSessionUtil.getCurrentSqlSession().getMapper(UserDao.class);
	
	@Override
	public int save(User user) {
		return userDao.save(user);
	}

	@Override
	public User login(String loginAct, String loginPwd, String clientIp) {
		return null;
	}

}
