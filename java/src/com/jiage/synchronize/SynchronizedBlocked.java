package com.jiage.synchronize;

import java.util.concurrent.TimeUnit;

/**
 * http://blog.csdn.net/javazejian
 * 
 * 
 * 对于synchronized来说，如果一个线程在等待锁，那么结果只有两种 1.要么它获得这把锁继续执行 2.要么它就保存等待
 * 即使调用中断线程的方法，也不会生效
 * 
 * 对本例来说，如果构造SynchronizedBlocked()先获取锁，run方法就无法获取锁；反之亦然
 * 
 * 构造和run()方法，谁先获取锁不一定
 */
public class SynchronizedBlocked implements Runnable {

	public synchronized void fun() {
		System.out.println(Thread.currentThread().getName() + " Trying to call fun()");
		while (true) // Never releases lock
			Thread.yield();
	}

	/**
	 * 在构造器中创建新线程并启动获取对象锁
	 */
	public SynchronizedBlocked() {
		// 该线程持有当前实例锁
		new Thread() {
			public void run() {
				this.setName("InnerThread");
				fun(); // Lock acquired by this thread
			}
		}.start();
	}

	public void run() {
		// 中断判断
		while (true) {
			if (Thread.interrupted()) {
				System.out.println("中断线程!!");
				break;
			} else {
				fun();// 一直处于获取锁的状态，中断对synchronized无效
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		SynchronizedBlocked sync = new SynchronizedBlocked();
		Thread t = new Thread(sync);
		// 启动后调用f()方法,构造和f()谁先获取锁不确定
		t.setName("TThread");
		t.start();
		TimeUnit.SECONDS.sleep(1);
		// 中断线程,无法生效
		t.interrupt();
	}
}





