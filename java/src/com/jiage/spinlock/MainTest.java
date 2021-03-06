package com.jiage.spinlock;

public class MainTest {
	public static void main(String[] args) throws InterruptedException {
		System.out.println("test SpinLock---------------------");
		testLock(new SpinLock());

		System.out.println("test ReentrantSpinLock------------");
		testLock(new ReentrantSpinLock());

		System.out.println("test TicketLock---------------");
		testLock(new TicketLock());

		System.out.println("test CLHLock------------------");
		testLock(new CLHLock());
		Thread.sleep(100);
		System.out.println("test CLHLock************");
		testLock(new CLHLock());

		System.out.println("test MCSLock----------------------");
		testLock(new MCSLock());
		Thread.sleep(100);
		System.out.println("test MCSLock************");
		testLock(new MCSLock());

	}

	static int count = 0;

	// 测试自旋锁
	private static void testLock(MyLock lock) {
		count = 0;
		long time = System.currentTimeMillis();
		Thread[] threads = new Thread[10];
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(() -> {
				try {
					lock.lock();
					if (!(lock instanceof SpinLock)) {// SpinLock不是可重入锁
						lock.lock();
					}
					Thread.sleep(130);
					System.out.println(Thread.currentThread().getName() + " run");
					count++;
					lock.unLock();
					if (!(lock instanceof SpinLock)) {
						lock.unLock();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
			threads[i].start();
		}

		for (Thread t : threads) {
			try {
				t.join();// 等待t线程完成，才继续当前线程--主线程
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		long currentTime = System.currentTimeMillis();
		System.out.println("count : " + count);
		System.out.println("time :" + (currentTime - time));
	}
}
