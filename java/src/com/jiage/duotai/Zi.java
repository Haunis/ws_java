package com.jiage.duotai;

public class Zi extends Fu {
//	private EntityBean bean = new EntityBean(this);
	public Zi() {
		System.out.println("子类构造 this : " + this);
	}

	@Override
	public void fun1() {
		System.out.println("子类的 fun1");
	}

	@Override
	public void fun2() {
		System.out.println("子类的 fun2");
	}

	/**
	 * 子类独有的方法
	 */
	public void ziFun() {
		System.out.println("子类的 ziFun");
	}
}
