# distributed-lock
distributed lock, segmentation lock, Base on Redis or Memcached

# example

    //对 segmentation_object 这个字符串加锁
  	ObjectLock objectSegmentationLock = new ObjectLock("segmentation_object");
  	
  	try {
  		
  		objectSegmentationLock.lock();
  		dosomething();
  		
  	} catch (Exception e) {
  		e.printStackTrace();
  	} finally {
  		
  		objectSegmentationLock.releaseLock();
  	}
  	
  	
  	try {
  		//尝试加锁直到5秒后超时加锁失败
  		while(objectSegmentationLock.tryLock(5000)) {
  			dosomething();
  		}
  		
  	} catch (Exception e) {
  		e.printStackTrace();
  	} finally {
  		
  		objectSegmentationLock.releaseLock();
  	}
  	
  	
  	CodeLock codeLock = new CodeLock();
  	
  	try {
  		//直接对以下代码（dosomething）进行加锁
  		codeLock.lock();
  		dosomething();
  		
  	} catch (Exception e) {
  		e.printStackTrace();
  	} finally {
  		
  		codeLock.releaseLock();
  	}
  	
  	try {
  		//直接对以下代码（dosomething）尝试加锁 直到5秒后超时加锁失败
  		while(codeLock.tryLock(5000)) {
  			dosomething();
  		}
  		
  	} catch (Exception e) {
  		e.printStackTrace();
  	} finally {
  		
  		codeLock.releaseLock();
  	}
		

