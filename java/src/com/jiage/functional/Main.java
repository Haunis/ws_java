package com.jiage.functional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 
 * @author BTI-PC-Z6602 函数式编程
 *         参考：https://www.cnblogs.com/snowInPluto/p/5981400.html
 *
 */
public class Main {
	public static void main(String[] args) {
		runNewThread();
		doMapTest();
	}

	private static void doMapTest() {
		List<Integer> result = Stream.of("3", "4").map(new Function<String, Integer>() {

			@Override
			public Integer apply(String str) {
				System.out.println(str);
				return 3;
			}
		}).collect(Collectors.toList());
		System.out.println("result : " + result);
	}

	private static void runNewThread() {
		System.out.println("Main Thread id : " + Thread.currentThread().getId());
		new Thread(() -> {
			System.out.println("Thread id : " + Thread.currentThread().getId());
		}).start();
	}
}
