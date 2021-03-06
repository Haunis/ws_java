package com.jiage.spinlock;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * 参考：https://blog.csdn.net/qq_34337272/article/details/81252853
 * 
 * 实现比较复杂
 * 
 * 基本思路：
 * 1.每个线程A持有一个node,并且此node指向下个线程B的node--通过B线程获取A线程的node,并将node的next指向B的node实现
 * 
 * 2.当前线程通过自旋当前的node状态，判断是否可以获取锁--当前线程node的状态由上一个线程释放锁时控制
 * 
 * 3.当某一个线程决定释放锁时，会判断nextNode是否存在：1.存在，则直接释放锁；2.不存在，则有两种可能:a.后续无线程，这时候直接释放锁
 * b.后面有线程，但未来得及将其node赋给 此线程，此时该线程原地等待后面线程赋值
 * 
 * 
 *
 */
public class MCSLock implements MyLock {
	private static class MCSNode {
		public volatile boolean isLocked = true;// 使用volatile修饰，否则unLock()nextNode.isLocked = false；不能同步到下个线程的lock()中
		public volatile MCSNode next;
	}

	private volatile MCSNode queue;
	private AtomicReferenceFieldUpdater<MCSLock, MCSNode> updater = AtomicReferenceFieldUpdater
			.newUpdater(MCSLock.class, MCSNode.class, "queue");
	private ThreadLocal<MCSNode> local = new ThreadLocal<MCSNode>();

	private volatile AtomicReference<Thread> atomicReference = new AtomicReference<Thread>();
	private int count = 0;// 可重入锁计数器

	@Override
	public void lock() {
		if (atomicReference.get() == Thread.currentThread()) {
			count++;
			return;
		}
//		System.out.println(Thread.currentThread().getName() + " ,in");
		MCSNode currentNode = new MCSNode();
		MCSNode preNode = updater.getAndSet(this, currentNode);// 有锁，自旋；如果前一个线程存在，则形成链表结构
		local.set(currentNode);
		if (preNode != null) {
			preNode.next = currentNode;
			while (currentNode.isLocked) {

			}
		}
		// 自旋. 调用FieldInstanceReadWrite.getAndSet()。最终调用UNSAFE.getAndSetObject()
		atomicReference.getAndSet(Thread.currentThread());
//		atomicReference.compareAndSet(null, Thread.currentThread());//无自旋，有风险

		// 相当于atomicReference.getAndSet(Thread.currentThread());//自旋
//		Thread t;
//		do {
//			t = atomicReference.get();
//			System.out.println("t : " + t + ", " + Thread.currentThread());
//		} while (!atomicReference.compareAndSet(t, Thread.currentThread()));

	}

	// 这个unlock设计的好巧妙。。
	@Override
	public void unLock() {
		if (count > 0) {
			count--;
			return;
		}
		MCSNode currentNode = local.get();
		MCSNode nextNode = currentNode.next;
		if (nextNode == null) {// 有两种情况：1.后续无节点 2.线程进来后但未来得及赋值,但更改了updater;
//			System.out.println(Thread.currentThread().getName() + ",nextNode = null");
			if (updater.compareAndSet(this, currentNode, null)) {// 设置成功代表后续无节点；设置不成功代表updater被其他线程更改过，有新线程进来，但还来得及赋值nextNode。
				atomicReference.getAndSet(null);
//				System.out.println(Thread.currentThread().getName() + ",set currentnode null");
				return;
			}
			while (currentNode.next == null) {// 新线程进来（调用lock()）后，在此等待新线程将其node赋给该线程的current.next
//				System.out.println(Thread.currentThread().getName() + " loop");
			}
			// 注意释放锁的顺序，先将atomicReference的当前thread设为null，再释放锁
			atomicReference.getAndSet(null);
//			System.out.println(Thread.currentThread().getName() + " ，1 unlock");
			currentNode.next.isLocked = false;
		} else {
			// 注意释放锁的顺序，先将atomicReference的当前thread设为null，再释放锁
			Thread t = atomicReference.getAndSet(null);
//			System.out.println(Thread.currentThread().getName() + ",2 unlock  : " + t);
			nextNode.isLocked = false;
		}
	}
}
