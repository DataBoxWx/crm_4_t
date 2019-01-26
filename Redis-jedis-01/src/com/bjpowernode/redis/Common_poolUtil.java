package com.bjpowernode.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Common_poolUtil {
	
	private static JedisPool pool;
	
	//创建线程池，池中是多个jedis对象
	
	public static JedisPool open(String host,int port){
		
		if(pool == null){
			//创建JedisPool对象
			
			
			JedisPoolConfig config = new JedisPoolConfig();
			//设置 线程池中最大存活量
			config.setMaxTotal(20);
			//设置空弦数
			config.setMaxIdle(2);
			//设置检查项，保证从线程池中取出的Jedis是可用的
			config.setTestOnBorrow(true);
			
			//创建JedisPool
			
			pool = new JedisPool(config, host, port, 6000);
		}
		
		return pool;
	}
	
	public static void close(){
		if(pool != null){
			pool.close();
			pool = null;
		}
	}
}
