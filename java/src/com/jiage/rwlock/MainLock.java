package com.jiage.rwlock;

/**
 * 读写锁
 * 
 * 参考：https://www.jianshu.com/p/87ac733fda80 
 * 
 * read模式共享锁，write模式互斥锁，readwrite模式互斥锁
 *
 */
public class MainLock {
	public static void main(String[] args) {
//		doReadTest();// 读-读不互斥，共享锁
		doWriteTest();// 写-写互斥，互斥锁
//		doReadWriteTest();//读-写互斥,互斥锁
	}

	public static void doReadTest() {
		MyReadWriteLock lock = new MyReadWriteLock();
		lock.put("key", "value");
		new Thread(() -> System.out.println(lock.get("key"))).start();
		new Thread(() -> System.out.println(lock.get("key"))).start();
		new Thread(() -> System.out.println(lock.get("key"))).start();
		new Thread(() -> System.out.println(lock.get("key"))).start();
		new Thread(() -> System.out.println(lock.get("key"))).start();
		new Thread(() -> System.out.println(lock.get("key"))).start();
	}

	public static void doWriteTest() {
		MyReadWriteLock lock = new MyReadWriteLock();
		new Thread(() -> lock.put("lin", "27")).start();
		new Thread(() -> lock.put("lin", "27")).start();
		new Thread(() -> lock.put("lin", "27")).start();
		new Thread(() -> lock.put("lin", "27")).start();
		new Thread(() -> lock.put("lin", "27")).start();
		new Thread(() -> lock.put("lin", "27")).start();
		new Thread(() -> lock.put("lin", "27")).start();
	}

	public static void doReadWriteTest() {
		MyReadWriteLock lock = new MyReadWriteLock();
		new Thread(() -> lock.put("lin", "27")).start();
		new Thread(() -> System.out.println(lock.get("lin"))).start();
		new Thread(() -> lock.put("lin", "28")).start();
		new Thread(() -> System.out.println(lock.get("lin"))).start();
	}
}
