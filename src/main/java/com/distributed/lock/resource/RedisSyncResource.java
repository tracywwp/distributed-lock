package com.distributed.lock.resource;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.alibaba.fastjson.JSONObject;

public class RedisSyncResource implements SyncResource{

	private JedisPool jedis;
	
	public boolean compareAndSwap(String key, Locker value) {
		Jedis jd = null;
		try {
			jd = jedis.getResource();
			return jd.setnx(key, JSONObject.toJSONString(value)) == 1l;
		} finally {
			if(jd != null)
				jedis.returnResource(jd);
		}
	}

	public Locker get(String key) {
		Jedis jd = null;
		try {
			jd = jedis.getResource();
			String value = jd.get(key);
			if(value == null) return null;
			return JSONObject.parseObject(value, Locker.class);
		}
		finally {
			if(jd != null)
				jedis.returnResource(jd);
		}
	}

	public boolean delete(String key) {
		Jedis jd = null;
		try {
			jd = jedis.getResource();
			return jd.del(key) > 0;
		}
		finally {
			if(jd != null)
				jedis.returnResource(jd);
		}
	}

	public void setJedis(JedisPool jedis) {
		this.jedis = jedis;
	}
	
	public JedisPool getJedis() {
		return jedis;
	}
}
