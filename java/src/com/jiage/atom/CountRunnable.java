package com.jiage.atom;

import java.util.concurrent.CountDownLatch;

public class CountRunnable implements Runnable {
	public static int mCount = 0;
	private CountDownLatch mCountDownLatch;

	public CountRunnable() {
	}

	public CountRunnable(CountDownLatch countDownLatch) {
		mCountDownLatch = countDownLatch;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10000; i++) {
			mCount++;
		}
		if (mCountDownLatch != null) {
			mCountDownLatch.countDown();
		}
	}
}
