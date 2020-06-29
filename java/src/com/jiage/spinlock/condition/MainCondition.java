package com.jiage.spinlock.condition;

/**
 * 
 * 参考： https://blog.csdn.net/javazejian/article/details/75043422
 * 
 * Reentrant-Condition类似synchronized 的wait-notify机制
 * 
 * Condition可实现同一锁下唤醒不同线程，synchronized无法做到
 *
 */
public class MainCondition {
	public static void main(String[] args) {
		ResourceByCondition rc = new ResourceByCondition();
		ConComsumeRun comsumeRun = new ConComsumeRun(rc);
		ConProduceRun produceRun = new ConProduceRun(rc);

		Thread t0 = new Thread(comsumeRun);
		Thread t1 = new Thread(comsumeRun);
		Thread t2 = new Thread(produceRun);
		Thread t3 = new Thread(produceRun);

		t0.start();
		t1.start();
		t2.start();
		t3.start();
	}
}
