package com.aaa.test;

public class BirthTest {
	public static void main(String[] args) {
		int birth = 19900623;
		for (int i = 2; i <= Math.sqrt(birth); i++) {
			if (birth % i == 0) {
				System.out.println(birth + "=" + i + "*" + birth / i);
			}
		}
	}
}
