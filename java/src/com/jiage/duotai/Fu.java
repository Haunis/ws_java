package com.jiage.duotai;

public class Fu {
	private EntityBean bean = new EntityBean(this);

	public Fu() {
		System.out.println("父类构造 this : " + this);
	}

	public void fun1() {
		System.out.println("父类的 fun1");
	}

	public void fun2() {
		System.out.println("父类的 fun2");
	}

	/**
	 * 父类独有的方法
	 */
	public void fuFun() {
		System.out.println("父类的 fuFun");
		Fu.this.fun2();//调用子类的fun2()
	}
}
