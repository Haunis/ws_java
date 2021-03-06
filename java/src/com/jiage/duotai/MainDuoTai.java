package com.jiage.duotai;

/**
 * 类加载顺序： 1.父类成员变量 2.父类构造 3.子类成员变量 4.子类构造
 *
 */
public class MainDuoTai {
	public static void main(String[] args) {
		Fu fuZi = new Zi();
		System.out.println("fuZi : " + fuZi);// com.jiage.duotai.Zi@48cf768c;实际上是子类的实例
		fuZi.fun1();// 子类的 fun1
		fuZi.fuFun();

		System.out.println("-----------------------");
//		Zi ziFu = (Zi)new Fu();//new 的类不可以直接转型
//		ziFu.fun1();

		System.out.println("new Fu()-----------------------");
		Fu fu = new Fu();
		fu.fuFun();

		System.out.println("new Zi-----------------------");
		Zi zi = new Zi();
		zi.fuFun();

	}
}
