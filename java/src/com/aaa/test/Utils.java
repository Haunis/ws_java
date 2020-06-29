package com.aaa.test;

public class Utils {
	/**
	 * 一组数的方差
	 */
	public static double getVariance(double[] array) {
		double sum = 0;
		for (double d : array) {
			sum += d * d;
		}
		return (sum - array.length * getAverage(array) * getAverage(array)) / array.length;
	}

	/**
	 * 一组数的平均数
	 */
	public static double getAverage(double[] array) {
		double sum = 0;
		for (double d : array) {
			sum += d;
		}
		return sum / array.length;
	}

	/**
	 * 是否是质数
	 */
	public static boolean isSu(int x) {
		if (x == 2) {
			return true;
		}
		for (int i = 2; i <= Math.sqrt(x); i++) {
			if (x % i == 0) {
				return false;
			}
		}
		return true;
	}
}
