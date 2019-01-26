package com.bjpowernode.crm.settings.service.impl;

import java.util.List;

import com.bjpowernode.crm.exception.LoginException;
import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.utils.DateUtil;
import com.bjpowernode.crm.utils.SqlSessionUtil;

public class UserServiceImpl implements UserService {

	private UserDao userDao = SqlSessionUtil.getCurrentSqlSession().getMapper(UserDao.class);
	
	@Override
	public int save(User user) {
		return userDao.save(user);
	}

	@Override
	public User login(String loginAct, String loginPwd, String clientIp) throws LoginException{
		//1、判断用户名和密码是否正确
		User user = userDao.getByLoginActAndLoginPwd(loginAct,loginPwd);
		if(user == null){
			throw new LoginException("登录账号或密码错误！");
		}
		//2、判断用户是否锁定
		if(user.getLockState() != null && !"".equals(user.getLockState())){
			if("1".equals(user.getLockState())){
				throw new LoginException("账户已锁定，请联系管理员！");	
			}
		}
		//3、判断用户是否失效
		if(user.getExpireTime() != null && !"".equals(user.getExpireTime())){
			if(DateUtil.getSysTime().compareTo(user.getExpireTime()) > 0){
				throw new LoginException("账户失效，请联系管理员！");
			}
		}
		//4、判断用户IP地址是否受限
		if(user.getAllowIps() != null && !"".equals(user.getAllowIps())){
			if(!user.getAllowIps().contains(clientIp)){
				throw new LoginException("IP地址受限，请联系管理员！");
			}
		}
		return user;
	}

	@Override
	public List<User> getAll() {
		return userDao.getAll();
	}

}













