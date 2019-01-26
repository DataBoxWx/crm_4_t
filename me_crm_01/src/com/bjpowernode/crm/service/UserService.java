package com.bjpowernode.crm.service;

import java.util.List;

import com.bjpowernode.crm.domain.User;
import com.bjpowernode.crm.util.LoginException;

public interface UserService {

	int checkAct(String loginAct);

	int doSave(User user);

	List<User> doList();

	int delete(String[] ids);

	User login(String loginAct, String loginPwd,String allowIp) throws LoginException;

}
