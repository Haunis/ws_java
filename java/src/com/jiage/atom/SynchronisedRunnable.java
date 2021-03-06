package com.jiage.atom;

import java.util.concurrent.CountDownLatch;

public class SynchronisedRunnable implements Runnable {
	public static int mCount = 0;
	private CountDownLatch mCountDownLatch;

	public SynchronisedRunnable() {

	}

	public SynchronisedRunnable(CountDownLatch countDownLatch) {
		this.mCountDownLatch = countDownLatch;
	}

	@Override
	public void run() {
		doCount();
		if (mCountDownLatch != null) {
			mCountDownLatch.countDown();
		}
	}

	private synchronized void doCount() {
		mCount++;
//		for (int i = 0; i < 10000; i++) {
//			mCount++;
//		}
	}
}
