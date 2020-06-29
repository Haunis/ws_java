package com.order;

/**
 * 
 * 选择排序和冒泡排序
 */
public class Main {
	public static void main(String[] args) {
		int[] array = new int[] { 33, 77, 1, 0, 5, 3 };
		printArray(array);

//		doSelectOrder(array);
		doPopOrder(array);

		printArray(array);
	}

	/**
	 * 相邻元素比较，一遍下来后，最大/最小元素已排在末尾
	 */
	private static void doPopOrder(int[] array) {
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length - i - 1; j++) {
				if (array[j] > array[j + 1]) {
					int temp = array[j];
					array[j] = array[j + 1];
					array[j + 1] = temp;
				}
			}
		}
	}

	/**
	 * 第i个数和后面所有的数进行比较，比较完之后，此i值存储最小的/最大的
	 */
	private static void doSelectOrder(int[] array) {
		for (int i = 0; i < array.length; i++) {
			for (int j = i + 1; j < array.length; j++) {
				if (array[i] > array[j]) {
					int temp = array[i];
					array[i] = array[j];
					array[j] = temp;
				}
			}
		}
	}

	private static void printArray(int[] array) {
		StringBuilder sb = new StringBuilder();
		for (int i : array) {
			sb.append(i).append(" ");
		}
		System.out.println(sb.toString());
	}
}
