package com.jiage.atom.reference;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceDemo {
	private static AtomicReference<User> atomicReference = new AtomicReference<User>();

	public static void main(String[] args) {
		User user = new User("Lin", 27);
		atomicReference.set(user);
		System.out.println("before : " + atomicReference.get());

		User user2 = new User("wang", 28);
		atomicReference.compareAndSet(user, user2);
		System.out.println("after : " + atomicReference.get());
	}
}
