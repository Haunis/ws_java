package com.jiage.wait;

public class MyThread extends Thread {
	Thread t = new Thread();

	@Override
	public void run() {
		fun();
	}

	public synchronized void fun() {
		synchronized (this) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
