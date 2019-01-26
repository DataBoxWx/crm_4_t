package com.bjpowernode.redis;

import java.util.Iterator;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class Redis_set {

	public static void main(String[] args) {
		String host = "192.168.10.129";
		int port = 6379;
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = Common_poolUtil.open(host, port);
			jedis = pool.getResource();
			
			Set<String> sets = jedis.smembers("sql");
			Iterator<String> iterator = sets.iterator();
			while (iterator.hasNext()) {
				System.out.println(iterator.next());
				
			}
			System.out.println("个好速："+jedis.scard("sql"));
			
			
		} finally {
			if(jedis != null){
				jedis.close();
			}
		}

	}

}
