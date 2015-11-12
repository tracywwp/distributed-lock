package com.distributed.lock.resource;

public class Locker {

	private String processId;
	
	private Long maxLifycycle;
	
	public Locker() {

	}

	public Locker(String processId, Long maxLifycycle) {
		super();
		this.processId = processId;
		this.maxLifycycle = maxLifycycle;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public Long getMaxLifycycle() {
		return maxLifycycle;
	}
	
	public void setMaxLifycycle(Long maxLifycycle) {
		this.maxLifycycle = maxLifycycle;
	}
}
