package com.bjpowernode.redis;

import java.util.List;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class Redis_list {

	public static void main(String[] args) {
		String host = "192.168.10.129";
		int port = 6379;
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = Common_poolUtil.open(host, port);
			jedis = pool.getResource();
			List<String> lrange = jedis.lrange("mylist", 0, -1);
			for (String string : lrange) {
				System.out.println(string);
			}
			Long linsert = jedis.linsert("mylist", LIST_POSITION.AFTER, "cc", "wowo");
			System.out.println(linsert);
			
		} finally {
			// TODO: handle finally clause
		}
	}

}
