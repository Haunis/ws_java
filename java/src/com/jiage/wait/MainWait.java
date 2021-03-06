package com.jiage.wait;

/**
 * 参考： wait: https://www.cnblogs.com/kevin-yuan/p/4112434.html
 * join:https://blog.csdn.net/a158123/article/details/78633772
 * 
 * 1.lock.wait()会让持有lock锁的当前线程释放锁，并且当前线程挂起等待；
 * 2.如果线程不持有lock的锁，而调用lock.wait()会报IllegalMonitorStateException
 *
 * 3.sleep() 不会释放锁
 * 
 * 4.join:若在t2 中调用t1.join()，意思就是t2等待t1结束再执行
 */
public class MainWait {
	static int total = 0;

	public static void main(String[] args) throws InterruptedException {
		String lock = "a";

		final Thread t1 = new Thread(() -> {
			synchronized (lock) {
				System.out.println("t1 等待t2完成计算");
				// 当前线程A等待
				try {
					lock.wait();// 持有lock monitor的此线程等待，并释放锁lock
//					Thread.currentThread().wait();//java.lang.IllegalMonitorStateException；Thread.currentThread()并无monitor
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("t1 计算的总和是：" + total);

			}
		});

		final Thread t2 = new Thread(() -> {
			synchronized (lock) {
				try {
					for (int i = 0; i < 100; i++) {
						total += i;
						Thread.sleep(20);
					}
					// （完成计算了）唤醒在此对象监视器上等待的单个线程，在本例中线程A被唤醒
					lock.notify();// 唤醒持有lock monitor的线程
					System.out.println("t2 计算完成");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		final Thread t3 = new Thread(() -> {
//			synchronized (t1) {
			System.out.println("t3 run");
			try {
				t1.join(0);// 有一点比较奇怪，此线程不持有t1的锁，但可以通过t1.join()调用t1.wait()。。。
//				t1.wait();// 不持有锁t1,所以不是t1 monitor的所有者,所以抛异常;使用synchronized即可，并且锁必须是调用wait()的t1;
				// 若锁是lock2,则lock2.wait()后，无其他地方notify,此线程会一直处于等待状态
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("t3 finish");
//			}
		});

		t1.start();
		t2.start();
		t3.start();
	}
}
