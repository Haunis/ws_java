package com.jiage.spinlock.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ResourceByCondition {
	boolean isProduceFinish = false;
	public volatile int count;

	ReentrantLock lock = new ReentrantLock();
	Condition con_producer = lock.newCondition();
	Condition con_consumer = lock.newCondition();

	public void produce() {
		lock.lock();
		while (isProduceFinish) {
			try {
				con_producer.await();
				System.out.println("produce await");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		count++;
		System.out.println("生产完成 " + count);
		isProduceFinish = true;
		con_consumer.signal();

		lock.unlock();
	}

	public void consume() {
		lock.lock();
		while (!isProduceFinish) {
			try {
				con_consumer.await();
				System.out.println("cosume await");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println("消费完成 " + count);
		isProduceFinish = false;
		con_producer.signal();

		lock.unlock();

	}
}
