package com.bjpowernode.redis;

import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

public class Redis_Tran {

	public static void main(String[] args) {
		String host = "192.168.10.129";
		int port = 6379;
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = Common_poolUtil.open(host, port);
			jedis = pool.getResource();
			
//			List<String> list = jedis.lrange("mylist", 0, -1);
//			
//			for (String string : list) {
//				System.out.println(string);
//			}
			
			//开启事务
			Transaction tran = jedis.multi();

			tran.lrange("mylist", 0, -1);

			
			
//			tran.lpush("mylist", "jvaa");
			tran.get("k4");
			List<Object> exec = tran.exec();
			for (int i = 0; i < exec.size();i++) {
				if(exec.get(i) instanceof List){
					for(int j = 0;j < ((List)exec.get(i)).size(); j++){
						System.out.println(((List)exec.get(i)).get(j));
					}
				}
				System.out.println("success :" + exec.get(i));
			}
			
		} finally {
			if(jedis != null){
				jedis.close();
			}
		}

	}

}
