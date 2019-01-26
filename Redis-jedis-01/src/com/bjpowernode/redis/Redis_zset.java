package com.bjpowernode.redis;

import java.util.Iterator;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

public class Redis_zset {

	public static void main(String[] args) {
		String host = "192.168.10.129";
		int port = 6379;
		JedisPool pool = null;
		Jedis jedis = null;
		
		try {
			pool  = Common_poolUtil.open(host, port);
			jedis = pool.getResource();
			Set<Tuple> tuple = jedis.zrangeByScoreWithScores("tree", "-inf", "+inf");
			Iterator<Tuple> iterator = tuple.iterator();
			while(iterator.hasNext()){
				Tuple tu = iterator.next();
				System.out.println("key:"+tu.getElement() + "     value : "+ tu.getScore());
			}
			
			
		} finally {
			if(jedis != null){
				jedis.close();
			}
		}

	}

}
