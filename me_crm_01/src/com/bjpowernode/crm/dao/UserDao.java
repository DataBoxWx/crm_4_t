package com.bjpowernode.crm.dao;

import java.util.List;

import com.bjpowernode.crm.domain.User;

public interface UserDao {
	/**
	 * 判断账户是否具有唯一性
	 * @param loginAct
	 * @return
	 */
	int checkAct(String loginAct);
	/**
	 * 新增用户
	 * @return
	 */
	int save(User user);
	/**
	 * 查询列表
	 * @return
	 */
	List<User>  list();
	/**
	 * 删除操作
	 * @param ids
	 * @return
	 */
	int delete(String[] ids);
	/**
	 * 登录验证
	 * @param loginAct
	 * @param loginPwd
	 * @return
	 */
	User login(String loginAct, String loginPwd);

}
