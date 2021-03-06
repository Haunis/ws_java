package com.jiage.spinlock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 参考：https://blog.csdn.net/qq_34337272/article/details/81252853 可重入自旋锁: lock()
 * 和 unLock() 要成对使用
 * 
 */
public class ReentrantSpinLock implements MyLock {
	private AtomicReference<Thread> atomicReference = new AtomicReference<Thread>();// 保存当前正在运行的线程
	private int count;

	@Override
	public void lock() {
		Thread current = Thread.currentThread();
		if (atomicReference.get() == current) {
			count++;
			return;
		}
		while (!atomicReference.compareAndSet(null, current)) {

		}
	}

	@Override
	public void unLock() {
		// 可以不加这个判断，因为unLock()只会在正在运行的线程里执行
		if (atomicReference.get() == Thread.currentThread()) {
			if (count > 0) {
				count--;
			} else {
				atomicReference.compareAndSet(Thread.currentThread(), null);
			}
		}
	}
}
