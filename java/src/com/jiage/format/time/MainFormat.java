package com.jiage.format.time;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 
 * 计算day天前的日期	 
 *
 */
public class MainFormat {
	public static void main(String[] args) {
		int day = 5;
		
		long currentTime = System.currentTimeMillis();
		long interval = day * 24 * 3600 * 1000;
		long lastTime = currentTime - interval;
		System.out.println(formatTime(lastTime));
	}

	public static String formatTime(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		String strTime = sdf.format(new Date(time));
		return strTime;
	}
}
