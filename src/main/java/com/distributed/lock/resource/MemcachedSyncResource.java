package com.distributed.lock.resource;

import net.rubyeye.xmemcached.MemcachedClient;

public class MemcachedSyncResource implements SyncResource{

	private MemcachedClient memcachedClient;
	
	public boolean compareAndSwap(String key, Locker value) {
		
		try {
			return memcachedClient.add(key, 0, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public Locker get(String key) {
		
		try {
			Object value = memcachedClient.get(key);
			if(value == null) return null;
			if(value instanceof Locker) {
				return (Locker) value;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean delete(String key) {
		
		try {
			return memcachedClient.delete(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void setMemcachedClient(MemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}
	
	public MemcachedClient getMemcachedClient() {
		return memcachedClient;
	}
}
