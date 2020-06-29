package com.aaa.test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AAAmain {
	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		System.out.println(date.getTime());

//		String strDate = sdf.format(new Date(date.getTime()));
		String strDate = sdf.format(1586258999389l);
		System.out.println(strDate);
	}
}
