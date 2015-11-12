package com.distributed.lock;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * 分布式对象锁
 * @author lichao
 *
 */
public class ObjectLock extends SegmentationLock{

	public ObjectLock(Object object) {
		super(
				"objectLock" + "@" + ReflectionToStringBuilder.reflectionToString(
						object,
						new NoHashCodeStringStyle())
			);
	}
	
	public ObjectLock(Integer target) {
		super("objectLock" + "@" + String.valueOf(target));
	}

	public ObjectLock(Long target) {
		super("objectLock" + "@" + String.valueOf(target));
	}

	public ObjectLock(Short target) {
		super("objectLock" + "@" + String.valueOf(target));
	}

	public ObjectLock(Byte target) {
		super("objectLock" + "@" + String.valueOf(target));
	}

	public ObjectLock(Boolean target) {
		super("objectLock" + "@" + String.valueOf(target));
	}

	public ObjectLock(Float target) {
		super("objectLock" + "@" + String.valueOf(target));
	}
	
	public ObjectLock(Double target) {
		super("objectLock" + "@" + String.valueOf(target));
	}
	
	public ObjectLock(String target) {
		super("objectLock" + "@" + String.valueOf(target));
	}
	
	
	public ObjectLock(Object object, long maxLifycycle) {
		super(
				"objectLock" + "@" + ReflectionToStringBuilder.reflectionToString(
						object,
						new NoHashCodeStringStyle()), maxLifycycle
			);
	}
	
	public ObjectLock(Integer target, long maxLifycycle) {
		super("objectLock" + "@" + String.valueOf(target), maxLifycycle);
	}

	public ObjectLock(Long target, long maxLifycycle) {
		super("objectLock" + "@" + String.valueOf(target), maxLifycycle);
	}

	public ObjectLock(Short target, long maxLifycycle) {
		super("objectLock" + "@" + String.valueOf(target), maxLifycycle);
	}

	public ObjectLock(Byte target, long maxLifycycle) {
		super("objectLock" + "@" + String.valueOf(target), maxLifycycle);
	}

	public ObjectLock(Boolean target, long maxLifycycle) {
		super("objectLock" + "@" + String.valueOf(target), maxLifycycle);
	}

	public ObjectLock(Float target, long maxLifycycle) {
		super("objectLock" + "@" + String.valueOf(target), maxLifycycle);
	}
	
	public ObjectLock(Double target, long maxLifycycle) {
		super("objectLock" + "@" + String.valueOf(target), maxLifycycle);
	}
	
	public ObjectLock(String target, long maxLifycycle) {
		super("objectLock" + "@" + String.valueOf(target), maxLifycycle);
	}
	
	static class NoHashCodeStringStyle extends ToStringStyle {
		
		private static final long serialVersionUID = -5205495672662130957L;

		public NoHashCodeStringStyle() {
			super.setUseIdentityHashCode(false);
		}
	}
}
