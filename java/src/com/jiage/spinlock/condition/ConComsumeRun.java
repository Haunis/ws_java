package com.jiage.spinlock.condition;

public class ConComsumeRun implements Runnable {

	ResourceByCondition rc;

	public ConComsumeRun(ResourceByCondition rc) {
		this.rc = rc;
	}

	@Override
	public void run() {
		while (rc.count < 9) {
			rc.consume();
		}
	}
}
