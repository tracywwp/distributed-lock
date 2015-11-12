package com.distributed.lock;

/**
 * 分布式锁
 * @author lichao
 *
 */
public interface DistributedLock {
	
	public boolean tryLock();

	public boolean tryLock(long timeout) throws InterruptedException;
	
	public void lock() throws InterruptedException;
	
	public void releaseLock();
	
}
