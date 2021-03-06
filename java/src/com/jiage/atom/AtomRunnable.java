package com.jiage.atom;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomRunnable implements Runnable {
	public static AtomicInteger atom = new AtomicInteger(0);
	private CountDownLatch mCountDownLatch;

	public AtomRunnable() {
	}

	public AtomRunnable(CountDownLatch countDownLatch) {
		mCountDownLatch = countDownLatch;
	}

	@Override
	public void run() {
		doCount();
		if (mCountDownLatch != null) {
			mCountDownLatch.countDown();
		}
	}

	private void doCount() {
		atom.incrementAndGet();

//		for (int i = 0; i < 10000; i++) {
//			atom.incrementAndGet();
//		}

//		for (int i = 0; i < 10000; i++) {
//			int value = atom.get();
//			atom.set(value + 1);
//			do {
//				value = atom.get();
//			} while (!atom.compareAndSet(value, value + 1));
//		}
	}
}
