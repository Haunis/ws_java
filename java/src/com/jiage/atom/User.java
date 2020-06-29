package com.jiage.atom;

public class User {
	public User() {
		System.out.println("user 构造方法被调用");
	}

	private String name;
	private int age;
	private static String id = "DEFAULT_ID";

	@Override
	public String toString() {
		return "User{" + "name='" + name + '\'' + ", age=" + age + '\'' + ", id=" + id + '\'' + '}';
	}
}
