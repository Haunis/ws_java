package com.jiage.spinlock;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * 参考：https://blog.csdn.net/qq_34337272/article/details/81252853
 *
 * 优化TicketLock:由每个线程对公有变量serviceNum的自旋改为对上个线程proNode的自旋
 * 
 * 思路：线程按顺序启动(顺序不定)，每个线程均有一个本地node。
 * 每个调用lock()的线程持有前一个的node,第一个线程持有的preNode为null--node链接成一个链表结构。
 * 当前一个线程调用unLock(),node的状态发生改变后。后一个线程检测到此node状态变动，自旋结束，后面的线程按这条链依次启动运行
 * 
 * 特点：隐式链表，没有后续节点属性；解决了多处理器缓存同步的问题
 *
 */
public class CLHLock implements MyLock {
	private static class CLHLockNode {
		// 注意用volatile修饰，否则unLock()currentNode.locked = false；不能同步到lock()中
		public volatile boolean locked = true;
	}

	private volatile CLHLockNode tail;
	private ThreadLocal<CLHLockNode> local = new ThreadLocal<CLHLockNode>();// 保存当前线程node
	// 设置当前线程的node,并获取上一个线程的node
	private AtomicReferenceFieldUpdater<CLHLock, CLHLockNode> updater = AtomicReferenceFieldUpdater
			.newUpdater(CLHLock.class, CLHLockNode.class, "tail");
	private AtomicReference<Thread> atomicReference = new AtomicReference<Thread>();
	private int count = 0;// 可重入锁计数器

	@Override
	public void lock() {
		if (atomicReference.get() == Thread.currentThread()) {
			count++;
			return;
		}

		CLHLockNode node = new CLHLockNode();
		local.set(node);

		CLHLockNode preNode = updater.getAndSet(this, node);// 有锁，自旋；形成链表结构
		if (preNode != null) {
			while (preNode.locked) {
			}
		}

//		atomicReference.compareAndSet(null, Thread.currentThread());
		atomicReference.getAndSet(Thread.currentThread());
	}

	@Override
	public void unLock() {
		if (count > 0) {
			count--;
			return;
		}
//		atomicReference.compareAndSet(Thread.currentThread(), null);
		atomicReference.getAndSet(null);
		CLHLockNode currentNode = local.get();
		currentNode.locked = false;// 最后释放锁
	}
}
