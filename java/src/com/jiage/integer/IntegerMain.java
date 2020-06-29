package com.jiage.integer;

/**
 * 超出[-128,127]常量池范围, 两个integer地址不会相等
 *
 */
public class IntegerMain {
	public static void main(String[] args) {
		int i_100 = 100;
		Integer integer_100 = Integer.valueOf(100);
		Integer integer1_100 = Integer.valueOf(100);
		System.out.println("i_100==integer_100 :" + (i_100 == integer_100));// true
		System.out.println("integer_100==integer1_100 :" + (integer_100 == integer1_100));// true

		int i_200 = 200;
		Integer integer_200 = Integer.valueOf(200);
		Integer integer1_200 = Integer.valueOf(200);
		System.out.println("i_200==integer_200 :" + (i_200 == integer_200));// true
		System.out.println("integer_200==integer1_200 :" + (integer_200 == integer1_200));// false

		System.out.println(integer_200.intValue() == integer1_200.intValue());// true

	}
}
