package com.jiage.bytestoint;

/**
 * byte数组和int互转，参考：https://www.cnblogs.com/liuyongdun/p/9780907.html
 * 负数的补码：按位取反＋１
 * 
 * 所以
 * int		byte[]
 * ０～127	0 0 0 0~127
 * 128~255  0 0 0 -128~-1
 */
public class ByteArrayToInt {
	public static void main(String[] args) {
		// 0000 0111
		// 128 64 32 16 8 4 2 1
		int i = 129;
		System.out.println("i=" + i);
		byte[] bytes = intToBytes(i);
		printArray(bytes);

		int j = bytesToInt(bytes);
		System.out.println("j=" + j);
	}

	// 将一个int型强制类型转换为byte型的时候，最高的三个字节会被砍掉，只留下最低的8位赋值给byte型。
	private static byte[] intToBytes(int i) {
		byte[] bytes = new byte[4];
		bytes[0] = (byte) i;
		bytes[1] = (byte) (i >> 8);
		bytes[2] = (byte) (i >> 16);
		bytes[3] = (byte) (i >> 24);
		return bytes;
	}

	// 对byte进行移位操作时，byte会自动补全(有符号位地补全)到32位再进行移位操作。
	// 最后进行|操作要排除高位符号位的影响，所以与0xff进行&运算
	private static int bytesToInt(byte[] bytes) {
		int byte0 = bytes[0] & 0xff;
		int byte1 = (bytes[1] & 0xff) << 8;
		int byte2 = (bytes[2] & 0xff) << 16;
		int byte3 = (bytes[3] & 0xff) << 24;
		return byte0 | byte1 | byte2 | byte3;
	}

	private static void printArray(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			sb.append(b + "\t");
		}
		System.out.println(sb);
	}
}
