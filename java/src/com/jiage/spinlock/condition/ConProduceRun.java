package com.jiage.spinlock.condition;

public class ConProduceRun implements Runnable {

	ResourceByCondition rc;

	public ConProduceRun(ResourceByCondition rc) {
		this.rc = rc;
	}

	@Override
	public void run() {
		while (rc.count < 9) {
			rc.produce();
		}
	}
}
