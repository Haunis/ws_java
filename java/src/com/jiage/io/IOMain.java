package com.jiage.io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

public class IOMain {
	public static void main(String[] args) {
		byte b = -1;
		String result = parseByte2HexStr(b);
		System.out.println("-1 =" + result);

		byte original = (byte) 0b11111111;// 补码
		System.out.println("0b11111111=" + original);

		readFileTest();

		byteFileToHex();
	}

	public static void byteFileToHex() {
		try {
			BufferedReader bfr = new BufferedReader(new FileReader("./src/com/jiage/io/data"));
			String str = null;
			int lineNumber = 0;
			while ((str = bfr.readLine()) != null) {
				lineNumber++;
//				System.out.println(/* lineNumber + " " + */str);
				String[] strArray = str.split("  ");
				byte[] bytess = new byte[strArray.length];
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < strArray.length; i++) {
					bytess[i] = Byte.parseByte(strArray[i]);
					sb.append(parseByte2HexStr(bytess[i])).append(" ");
				}
				System.out.println(sb);
			}
			bfr.close();
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}

	public static String parseByte2HexStr(byte b) {
		StringBuffer sb = new StringBuffer();
		String hex = Integer.toHexString(b & 0xFF);
		if (hex.length() == 1) {
			hex = '0' + hex;
		}
		sb.append(hex.toUpperCase());
		return sb.toString();
	}

	public static void readFileTest() {
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream("./src/com/jiage/io/111.txt");
//            byte[] bys = new byte[4];
//            int len = inputStream.read(bys);
//            System.out.println(new String(bys));  //bcd
//            System.out.println(len);  //3
//            System.out.println(inputStream.read(bys));  //-1
			int len = 0;
			byte[] bys = new byte[1024];
			while ((len = inputStream.read(bys)) != -1) {
				System.out.println(new String(bys, 0, len));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
