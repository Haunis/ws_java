package com.jiage.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 写文件后，记得刷新工程
 *
 */
public class FileUtils {
	public static File createNewFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			// 先得到文件的上级目录，并创建上级目录，再创建文件
			file.getParentFile().mkdir();
			try {
				// 创建文件
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println(e.toString());
			}
		} else {
			System.out.println("exists ");
		}
		return file;
	}

	public static void writeToFile(File destFile, String sourceMsg) {
		try {
			FileWriter fw = new FileWriter(destFile, true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(sourceMsg);
			bw.newLine();
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		File file = createNewFile("./src/com/jiage/io/111.txt");
		writeToFile(file, "111");
		writeToFile(file, "222");
		writeToFile(file, "333");

//		File file2 = createNewFile("./aaa.txt");
//		writeToFile(file2, "aaa");
//		writeToFile(file2, "bbb");
//		writeToFile(file2, "ccc");
	}
}
