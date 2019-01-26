package com.bjpowernode.crm.service.impl;

import java.util.List;

import com.bjpowernode.crm.dao.UserDao;
import com.bjpowernode.crm.domain.User;
import com.bjpowernode.crm.service.UserService;
import com.bjpowernode.crm.util.DateUtil;
import com.bjpowernode.crm.util.LoginException;
import com.bjpowernode.crm.util.SqlSessionUtil;

public class UserServiceImpl implements UserService {
	private UserDao userDao= SqlSessionUtil.getCurrentSqlSession().getMapper(UserDao.class);
	@Override
	public int checkAct(String loginAct) {
		int count = userDao.checkAct(loginAct);
		return count;
	}
	@Override
	public int doSave(User user) {
		
		return userDao.save(user);
	}
	@Override
	public List<User>  doList() {
		
		return userDao.list();
	}
	@Override
	public int delete(String[] ids) {
	
		return userDao.delete(ids);
	}
	@Override
	public User login(String loginAct, String loginPwd,String allowIp) throws LoginException {
		User user = userDao.login(loginAct,loginPwd);
		if(user == null){
			throw new LoginException("账户名或密码错误");
		}
		if(user.getLockState() != null && !user.getLockState().equals("")){
			if(user.getLockState() == "1"){
				throw new LoginException("账户已锁定，请联系管理员");
			}
		}
		if(user.getExpireTime() != null && !user.getExpireTime().equals("")){
			if(user.getExpireTime().compareTo(DateUtil.getSysTime())<0){
				throw new LoginException("账户已失效，请联系管理员");
			}
		}
		if(user.getAllowIp() != null && !"".equals(user.getAllowIp())){
			if(!user.getAllowIp().contains(allowIp)){
				throw new LoginException("受限的Ip,请联系管理员");
			}
		}
		
		
		return user;
	}

}
