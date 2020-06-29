package com.jiage.duotai2;

public class B extends A {
	public String show(A obj) {
		return ("B and A : " + obj);
	}

	public String show(B obj) {
		return ("B and B : " + obj);
	}

}
