package com.jiage.atom;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

public class UnsafeDemo {
	public static void main(String[] args) throws NoSuchFieldException, SecurityException, InstantiationException,
			IllegalArgumentException, IllegalAccessException {
//		Unsafe un = Unsafe.getUnsafe();//报SecurityException，此方法是给Bootstrap类加载器使用
		// 通过反射得到theUnsafe对应的Field对象
		Field fieldTheUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
		// 设置该Field为可访问
		fieldTheUnsafe.setAccessible(true);
		// 通过Field得到该Field对应的具体对象，传入null是因为该Field为static的
		Unsafe unsafe = (Unsafe) fieldTheUnsafe.get(null);
		System.out.println("unsafe : " + unsafe);

		// 通过allocateInstance直接创建对象
		User user = (User) unsafe.allocateInstance(User.class);

		System.out.println("allocateInstance()=" + user);// User{name='null', age=0', id=DEFAULT_ID'}
		Class<? extends User> userClass = user.getClass();
		Field name = userClass.getDeclaredField("name");
		Field age = userClass.getDeclaredField("age");
		Field id = userClass.getDeclaredField("id");

		// 获取实例变量name和age在对象内存中的偏移量并设置值
		unsafe.putInt(user, unsafe.objectFieldOffset(age), 18);
		unsafe.putObject(user, unsafe.objectFieldOffset(name), "android TV");
		System.out.println("getInt() : " + unsafe.getInt(user, unsafe.objectFieldOffset(age)));
		// 这里返回 【User.class】
		Object staticBase = unsafe.staticFieldBase(id);
		System.out.println("staticBase:" + staticBase);

		// 获取静态变量id的偏移量staticOffset
		long staticOffset = unsafe.staticFieldOffset(userClass.getDeclaredField("id"));
		// 获取静态变量的值
		System.out.println("before getObject():" + unsafe.getObject(staticBase, staticOffset));
		// 设置值
		unsafe.putObject(staticBase, staticOffset, "SSSSSSSS");
		// 获取静态变量的值
		System.out.println("after getObject():" + unsafe.getObject(staticBase, staticOffset));
		// 输出USER
		System.out.println("输出USER:" + user.toString());

		long data = 1000;
		byte size = 1;// 单位字节

		// 调用allocateMemory分配内存,并获取内存地址memoryAddress
		long memoryAddress = unsafe.allocateMemory(size);
		// 直接往内存写入数据
		unsafe.putAddress(memoryAddress, data);
		// 获取指定内存地址的数据
		long addrData = unsafe.getAddress(memoryAddress);
		System.out.println("addrData:" + addrData);
	}
}
