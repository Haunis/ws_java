package com.jiage.excel;

public interface ResultListener {
	void onError(String info); 
	void onComplete();
}
