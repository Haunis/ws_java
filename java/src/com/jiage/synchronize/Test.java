package com.jiage.synchronize;

public class Test {
	public synchronized void fun() {
		System.out.println("fun");
	}

	public static synchronized void fun2() {
		System.out.println("fun2");
	}

	public void fun3() {
		synchronized (this) {
			System.out.println("fun3");
		}
	}
}
