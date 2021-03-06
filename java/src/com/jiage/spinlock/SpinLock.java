package com.jiage.spinlock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 参考：https://blog.csdn.net/qq_34337272/article/details/81252853
 * 
 * 普通自旋锁
 * 
 * 思路：一个线程拿到锁后，在此线程释放锁前，其他线程均在自旋。待此线程释放锁后，其他线程一起竞争锁
 * 
 * 本例中的锁的实现--atomicReference保存的正在运行的线程
 * 
 * 缺点：不可再重入
 *
 */
public class SpinLock implements MyLock {
	private static AtomicReference<Thread> atomicReference = new AtomicReference<Thread>();

	@Override
	public void lock() {
		while (!atomicReference.compareAndSet(null, Thread.currentThread())) {

		}
	}

	@Override
	public void unLock() {
		atomicReference.compareAndSet(Thread.currentThread(), null);
	}
}
