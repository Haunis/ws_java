package com.jiage.fanxing;

import java.util.List;

public class Utils<T> {
	public T getFirst(List<T> list) {
		return list.get(0);
	}

	//通配符。以Fruit为例，表示可以接受List<Apple>,List<Orange>,List<Fruit>
	public T getFirst2(List<? extends T> list) {
		return list.get(0);
	}

	//边界符。【【【泛型方法，和泛型类里的Utils<T>不要混淆了】】】
	//这个是真正的泛型方法
	public <E extends Fruit> E getFirst3(List<E> list) {
		return list.get(0);
	}

	public T put(List<T> list, T t) {
		list.add(t);
		return t;
	}

	public T put2(List<? super T> list, T t) {
		list.add(t);
		return t;
	}
	public static <E>void printArray(E[] array) {
		StringBuilder sb = new StringBuilder();
		for(E e:array) {
			sb.append(e).append("\t");
		}
		System.out.println(sb.toString());
	}
}
