package com.jiage.utils;

import java.awt.Toolkit;

public class Utils {
	public static int getScreenWidth() {
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		return width;
	}
	public static int getScreenHeight() {
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		return height;
	}
}
