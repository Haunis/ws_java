package com.jiage.atom;

import java.util.concurrent.CountDownLatch;

public class AtomMain {
	private static int mThreadCount = 100000;

	public static void main(String[] args) throws InterruptedException {
		doCountDownLatchTest();
		doSynchronisedTest();
		doAtomTest();
	}

	public static void doAtomTest() throws InterruptedException {
		long time = System.currentTimeMillis();
		CountDownLatch countDownLatch = new CountDownLatch(mThreadCount);
		AtomRunnable runnable = new AtomRunnable(countDownLatch);
		for (int i = 0; i < mThreadCount; i++) {
			new Thread(runnable).start();
		}
		countDownLatch.await();
//		if (Thread.activeCount() > 1) {
//			Thread.yield();//yield不可靠，并不是单纯的让给其他线程，而是让自己和其他线程同时竞争
//		}
//		System.out.println("activeCount  : " + Thread.activeCount());
		System.out.println("atom间隔：" + (System.currentTimeMillis() - time));
		System.out.println("mCount=" + runnable.atom.get());
	}

	public static void doSynchronisedTest() throws InterruptedException {
		long time = System.currentTimeMillis();
		CountDownLatch countDownLatch = new CountDownLatch(mThreadCount);
		SynchronisedRunnable runnable = new SynchronisedRunnable(countDownLatch);
		for (int i = 0; i < mThreadCount; i++) {
			new Thread(runnable).start();
		}
		countDownLatch.await();
		System.out.println("synchronised间隔：" + (System.currentTimeMillis() - time));
		System.out.println("mCount=" + runnable.mCount);
	}

	public static void doCountDownLatchTest() throws InterruptedException {
		long time = System.currentTimeMillis();
		CountDownLatch countDownLatch = new CountDownLatch(mThreadCount);
		CountRunnable runnable = new CountRunnable(countDownLatch);
		for (int i = 0; i < mThreadCount; i++) {
			new Thread(runnable).start();
		}
		countDownLatch.await();
		System.out.println("countDownLatch间隔：" + (System.currentTimeMillis() - time));
		System.out.println("mCount=" + runnable.mCount);
	}
}
