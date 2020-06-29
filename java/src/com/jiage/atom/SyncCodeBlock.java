package com.jiage.atom;

public class SyncCodeBlock {
	public int i;

	public void syncTask() {
		// 同步代码库
		synchronized (this) {
			i++;
		}
	}
}
