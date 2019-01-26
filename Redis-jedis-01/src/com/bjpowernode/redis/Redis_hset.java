package com.bjpowernode.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class Redis_hset {

	public static void main(String[] args) {
		
		
		
		JedisPool pool = null;
		Jedis jedis = null;
		
		try {
			pool = Common_poolUtil.open("192.168.10.129", 6379);
			
			jedis = pool.getResource();
			
			String hget = jedis.hget("web", "baidu");
			System.out.println(hget);
		} catch (Exception e) {
			e.getMessage();
		}finally {
			if(jedis != null){
				jedis.close();
			}
				
		}

	}

}
