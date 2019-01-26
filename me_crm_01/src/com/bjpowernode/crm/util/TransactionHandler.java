package com.bjpowernode.crm.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.apache.ibatis.session.SqlSession;

public class TransactionHandler implements InvocationHandler {

	private Object target;

	public TransactionHandler(Object target) {
		this.target = target;
	}

	public Object getProxy() {
		return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		SqlSession sqlSession = null;
		Object retValue = null;
		try {
			sqlSession = SqlSessionUtil.getCurrentSqlSession();

			retValue = method.invoke(target, args); 

			sqlSession.commit();
		} catch (Exception e) {
			SqlSessionUtil.rollback(sqlSession);
			e.printStackTrace();
			throw e.getCause();
		} finally {
			SqlSessionUtil.close(sqlSession);
		}
		return retValue;
	}

}
