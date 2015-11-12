package com.distributed.lock;

import java.lang.management.ManagementFactory;

import com.distributed.lock.resource.Locker;
import com.distributed.lock.resource.Settings;
import com.distributed.lock.resource.SyncResource;

/**
 * 分段锁
 * @author lichao
 *
 */
public class SegmentationLock implements DistributedLock{

	protected String segmentation;
	
	protected long maxLifycycle;
	
	private Locker selfLock;
	
	private SyncResource sync = Settings.getSyncResource();
	
	protected SegmentationLock(String segmentation) {
		this(segmentation, System.currentTimeMillis() + Settings.DEFAULT_LOCK_LIFYCYCLE);
	}
	
	protected SegmentationLock(String segmentation, long maxLifycycle) {
		if(maxLifycycle <= System.currentTimeMillis()) {
			throw new IllegalArgumentException("maxLifycycle must great than now");
		}
		this.segmentation = Settings.getLockPrefix() + "@" + segmentation;
		this.selfLock = new Locker(getLockOwner(), maxLifycycle);
		this.maxLifycycle = maxLifycycle;
	}

	public boolean tryLock() {
		
		Locker ownerLock = sync.get(segmentation);
		
		if(ownerLock == null) {
		
			return sync.compareAndSwap(segmentation, selfLock);
		
		}
		
		//额外校验机制，防止其他客户端没有释放锁，防止死锁机制
		//lock is timeout
		if(isTimeout(ownerLock)) {
			
			SegmentationLock timeoutLock = new SegmentationLock(Settings.TIMEOUT_LOCK_SEGMENTATIO + "@" + segmentation);

			try {
				
				//加锁 防止多次释放
				if(timeoutLock.tryLock()) {
					
					//release this lock
					sync.delete(segmentation);
					
					//tryLock again
					return sync.compareAndSwap(segmentation, selfLock);
					
				}
				
				return false;

			} finally {
				
				//release timeoutLock.lock
				timeoutLock.releaseLock();
			}
			
		}
		
		return reentryable(ownerLock, selfLock);
		
	}

	public boolean tryLock(long timeout) throws InterruptedException{

		long first = System.currentTimeMillis();
		
		if(timeout <= first) {
			throw new IllegalArgumentException("timeout must great than now");
		}
		
		for(;;) {
			
			if(!tryLock() && !Thread.interrupted()) {
				
				long last = System.currentTimeMillis();
				
				timeout -= last - first; 
				
				if(timeout <= 0) {
					
					return false;
				}
				
				continue;
			}
			
			return true;
		}
	}

	public void lock() throws InterruptedException{
		
		for(;;) {
			
			if(tryLock() && !Thread.interrupted()) {
				
				break;
			}
			
		}
		
	}

	public void releaseLock() {
		
		Locker ownerLock = sync.get(segmentation);

		if(ownerLock == null) return;
		
		//删除前确认锁是自己持有的
		if(reentryable(ownerLock, selfLock)) {
			
			//删除前确认锁没有过期
			if(!isTimeout(ownerLock)) {
				
				sync.delete(segmentation);
			}
		}
		
	}
	
	/**
	 * 是否过期
	 * @param locker
	 * @return
	 */
	private boolean isTimeout(Locker locker) {

		return System.currentTimeMillis() > locker.getMaxLifycycle();
		
	}
	
	/**
	 * 是否可重入
	 * @param oldLocker
	 * @param newLocker
	 * @return
	 */
	private boolean reentryable(Locker ownerLock, Locker selfLock) {
		
		return ownerLock.getProcessId().equals(selfLock.getProcessId());

	}
	
	private String getLockOwner() {
		
		String name = ManagementFactory.getRuntimeMXBean().getName();
		
		return name.split("@")[0];
	}
	
}
