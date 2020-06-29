package com.jiage.duotai2;
/**
 * 方法只接受类及其子类，不接受类的父类
 *
 */
public class MainDuoTai2 {
	public static void main(String[] args) {
		A a = new A();
		A ab = new B();
		B b = new B();
		C c = new C();
		D d = new D();

		System.out.println("1--" + a.show(b));//A and A
		System.out.println("2--" + a.show(c));//A and A
		System.out.println("3--" + a.show(d));//A and D
		System.out.println("4--" + ab.show(b));//B and A
		System.out.println("5--" + ab.show(c));//B and A
		System.out.println("6--" + ab.show(d));//A and D
		System.out.println("7--" + b.show(b));//B and B
		System.out.println("8--" + b.show(c));//B and B
		System.out.println("9--" + b.show(d));//A and D
	}
}
