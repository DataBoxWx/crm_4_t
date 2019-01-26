package com.bjpowernode.redis;

import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisPrac {
	
	public static void main(String[] args) {
		String host = "192.168.10.129";
		int port  = 6379;
		JedisPool pool = null;
		Jedis jedis = null;
		
		try {
			
			pool = Common_poolUtil.open(host, port);
			jedis = pool.getResource();
			
			jedis.lpush("mylist", "hah","oo","daad");
			List<String> lrange = jedis.lrange("mylist", 0, -1);
			for (String string : lrange) {
				System.out.println("mylist:" + string);
			}
			
		} finally {
			if(jedis != null){
				
				jedis.close();
			}
		}
	}
}
