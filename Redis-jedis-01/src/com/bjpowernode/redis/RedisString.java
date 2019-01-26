package com.bjpowernode.redis;

import java.util.Set;

import redis.clients.jedis.Jedis;

public class RedisString {
	
	public static void main(String[] args) {
		
		String host = "192.168.10.129";
		int port  = 6379;
		Jedis jedis = new Jedis(host, port);
		
		jedis.zadd("tree", 14, "lucy");
		
		Set<String> zrangeByScore = jedis.zrangeByScore("tree", "-inf", "+inf");
		for (String string : zrangeByScore) {
			System.out.println("tree:" + string);
		}
	}
}
