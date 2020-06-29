package com.jiage.reflect;

public class Wrapper {
	public static final class ClientID {
		public ClientID() {
			System.out.println("ClientID construct() excute");
		}

		/**
		 * adas背景
		 */
		public static final String BACKGROUND = "app_clientId.background";
		/**
		 * 无媒体
		 */
		public static final String ACTIVE_DEFAULT = "app_clientId.no_media";
		/**
		 * 微信
		 */
		public static final String WECHAT = "app_clientId.wechat";
	}
}