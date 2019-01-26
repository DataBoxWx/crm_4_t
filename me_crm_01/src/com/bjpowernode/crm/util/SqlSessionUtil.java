package com.bjpowernode.crm.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SqlSessionUtil {

	private static SqlSessionFactory sqlSessionFactory;
	private static ThreadLocal<SqlSession> local = new ThreadLocal<>();

	static {
		try {
			InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取当前的SqlSession对象
	 * 
	 * @return
	 */
	public static SqlSession getCurrentSqlSession() {
		// 从当前线程中获取SqlSession
		SqlSession sqlSession = local.get();
		if (sqlSession == null) {
			sqlSession = sqlSessionFactory.openSession(); // 第一次获取
			local.set(sqlSession); // 绑定
		}
		return sqlSession;
	}

	/**
	 * 关闭资源
	 * 
	 * @param sqlSession
	 */
	public static void close(SqlSession sqlSession) {
		if (sqlSession != null) {
			sqlSession.close();
			local.remove();
		}
	}

	/**
	 * 回滚事务
	 * 
	 * @param sqlSession
	 */
	public static void rollback(SqlSession sqlSession) {
		if (sqlSession != null) {
			sqlSession.rollback();
		}
	}

}
