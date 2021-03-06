package com.jiage.reflect;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.jiage.reflect.Wrapper.ClientID;

public class Main {
	public static void main(String[] args) throws Exception {
//		if (!init("3")) {
		if (!init("app_clientId.background")) {
			throw new Exception("not ok");
		}
		System.out.println(3333333);
	}

	public static boolean init(String clientId) {
		try {
			Field[] fields = Wrapper.ClientID.class.getFields();
//			ClientID clientID = new Wrapper.ClientID();
			ArrayList<String> list = new ArrayList<String>();
			for (Field field : fields) {
				System.out.println("name : " + field.getName() + "\tvalue : " + field.get(ClientID.class));
//				list.add((String) field.get(clientID));
				list.add((String) field.get(ClientID.class));// 实例或者.class均可
			}
			return list.contains(clientId);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();

		}
		return false;
	}
}
