package com.distributed.lock.resource;

import com.alibaba.fastjson.JSONObject;
import com.qding.framework.common.redis.ShardedJedisClient;

public class RedisSyncResource implements SyncResource{

	private ShardedJedisClient jedis;
	
	public boolean compareAndSwap(String key, Locker value) {
		return jedis.setnx(key, JSONObject.toJSONString(value)) == 1l;
	}

	public Locker get(String key) {
		String value = jedis.get(key);
		if(value == null) return null;
		return JSONObject.parseObject(value, Locker.class);
	}

	public boolean delete(String key) {
		return jedis.del(key) > 0;
	}

	public void setJedis(ShardedJedisClient jedis) {
		this.jedis = jedis;
	}
	
	public ShardedJedisClient getJedis() {
		return jedis;
	}
}
