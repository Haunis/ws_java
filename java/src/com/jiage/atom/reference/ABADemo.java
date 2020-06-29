package com.jiage.atom.reference;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * atomicStampedR.compareAndSet()注意IntegerCache 在[-128,127]范围之外要使传 Integer
 * integer = new Integer(xxx);
 * 
 * atomicStampedR.compareAndSet()比较的是地址
 * 
 *
 */
public class ABADemo {
	static Integer integer = Integer.valueOf(200);
	static AtomicInteger atIn = new AtomicInteger(100);

	// 初始化时需要传入一个初始值和初始时间
	static AtomicStampedReference<Integer> atomicStampedR = new AtomicStampedReference<Integer>(100, 0);

	// AtomicInteger示例----------------------------------------------------------------------------------
	static Thread t1 = new Thread(new Runnable() {
		@Override
		public void run() {
			// 更新为200
			atIn.compareAndSet(100, 200);
			// 更新为100
			atIn.compareAndSet(200, 100);
		}
	});

	static Thread t2 = new Thread(new Runnable() {
		@Override
		public void run() {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			boolean flag = atIn.compareAndSet(100, 500);
			System.out.println("t2  flag=" + flag + ",newValue=" + atIn);
		}
	});

	// AtomicStampedReference示例---------------------------------------------------------------------
	static Thread t3 = new Thread(new Runnable() {
		@Override
		public void run() {
			int time = atomicStampedR.getStamp();
			// 更新为200;超过[-128,127]要传Integer
			boolean flag = atomicStampedR.compareAndSet(100, integer, time, time + 1);
			System.out.println("t3  flag=" + flag + ",newValue=" + atomicStampedR.getReference());

			// 更新为100
			int time2 = atomicStampedR.getStamp();
			flag = atomicStampedR.compareAndSet(integer, 100, time2, time2 + 1);
			System.out.println("t3  flag=" + flag + ",newValue=" + atomicStampedR.getReference());
		}
	});

	static Thread t4 = new Thread(new Runnable() {
		@Override
		public void run() {
			int time = atomicStampedR.getStamp();
			System.out.println("t4  sleep 前 t4 time:" + time);
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//			time = atomicStampedR.getStamp();//这个时候取时间就可以修改成功
			boolean flag = atomicStampedR.compareAndSet(100, 500, time, time + 1);
			System.out.println("t4  flag=" + flag + ",newValue=" + atomicStampedR.getReference());
		}
	});

	public static void main(String[] args) throws InterruptedException {
		t1.start();
		t2.start();
		t1.join();
		t2.join();

		t3.start();
		t4.start();
	}
}
