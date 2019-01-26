package com.bjpowernode.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.bjpowernode.dao.UserDao;
@Service(value="userServiceImpl")
public class UserServiceImpl implements UserService {
	
	@Autowired
	@Qualifier(value="userDaoImpl")
	private UserDao userDao;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	@Override
	public void addUser() {
		userDao.insert();
	}

}
