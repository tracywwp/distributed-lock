package com.distributed.lock.resource;

public interface SyncResource {

	/**
	 * 
	 * @param key
	 * @param value is exceptValue , current value should be NULL
	 * @return
	 */
	public boolean compareAndSwap(String key, Locker value);
	
	public Locker get(String key);
	
	public boolean delete(String key);
}
