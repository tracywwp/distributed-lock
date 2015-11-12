package com.distributed.lock;

import com.distributed.lock.resource.Settings;


/**
 * 分布式代码锁
 * @author lichao
 *
 */
public class CodeLock extends SegmentationLock{

	public CodeLock() {
		super(null);
	}
	
	public CodeLock(long maxLifycycle) {
		super(null, maxLifycycle);
	}
	
	@Override
	public boolean tryLock() {
		super.segmentation = getCodeLine(Thread.currentThread().getStackTrace());
		return super.tryLock();
	}
	
	@Override
	public boolean tryLock(long timeout) throws InterruptedException {
		super.segmentation = getCodeLine(Thread.currentThread().getStackTrace());
		return super.tryLock(timeout);
	}
	
	@Override
	public void lock() throws InterruptedException{
		super.segmentation = getCodeLine(Thread.currentThread().getStackTrace());
		super.lock();
	}
	
	@Override
	public void releaseLock() {
		super.releaseLock();
	}

	public String getCodeLine(StackTraceElement stack[]) {
		
		StackTraceElement parentStack = stack[1];
		
        return Settings.getLockPrefix()
        		+ "@" + "codeLock"
        		+ "@" + parentStack.getClassName() 
        		+ "@" + parentStack.getMethodName()
        		+ "@" + parentStack.getLineNumber();  
        
	}
}
