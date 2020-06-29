package com.jiage.spinlock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 参考：https://blog.csdn.net/qq_34337272/article/details/81252853
 * 
 * 公平锁：先排队的先拿到锁
 * 
 * 思路：每个线程都持有一个流水号，号码依次增大；持有小号的线程释放锁后，持有大号的线程自旋后激活
 * 
 * 缺点：不同线程读取serviceNum，每次读写操作都必须在多个处理器缓存之间进行缓存同步，这会导致繁重的系统总线和内存的流量
 *
 */
public class TicketLock implements MyLock {
	private ThreadLocal<Integer> localTicketNum = new ThreadLocal<Integer>();// 每个线程持有的号码
	private AtomicReference<Thread> atomicReference = new AtomicReference<Thread>();// 存储当前线程
	private AtomicInteger ticketNum = new AtomicInteger(0);// 每个线程的排队号
	private AtomicInteger serviceNum = new AtomicInteger(1);// 当前正执行的号.主内存自旋
	private int count = 0;// 可重入锁使用

	@Override
	public void lock() {
		Thread current = Thread.currentThread();
		if (atomicReference.get() == current && serviceNum.get() == localTicketNum.get()) {
			count++;
			return;
		}
		localTicketNum.set(ticketNum.incrementAndGet());// 给【当前线程】设置排队号码
		while (serviceNum.get() != localTicketNum.get()) {// 还没等到当前线程的号码，自旋

		}
//		atomicReference.compareAndSet(null, current);
		atomicReference.getAndSet(current);
	}

	@Override
	public void unLock() {
		Thread current = Thread.currentThread();
		if (atomicReference.get() == current) {
			if (count > 0) {
				count--;
			} else {
//				atomicReference.compareAndSet(current, null);
				atomicReference.getAndSet(null);
				int value = localTicketNum.get();
				serviceNum.compareAndSet(value, value + 1);
			}
		}
	}
}
