package com.jiage.spinlock;

import java.util.concurrent.locks.ReentrantLock;

public class MainDebugReentractLock {

	public static void main(String[] args) throws InterruptedException {

		ReentrantLock lock = new ReentrantLock();
		Thread t0 = new Thread(() -> {
			System.out.println("t0 in");//此处打断点，下一步进lock方法
			lock.lock();
			System.out.println("t0 run");
			lock.unlock();
		});

		Thread t1 = new Thread(() -> {
			lock.lock();
			try {
				Thread.sleep(500*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("t1 run");
			lock.unlock();
		});

		t0.start();
		t1.start();

		t0.join();
		t1.join();
		System.out.println("finished ");
	}
}
