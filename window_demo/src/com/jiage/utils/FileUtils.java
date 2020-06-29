package com.jiage.utils;

import java.io.File;

public class FileUtils {
	private static final String FILE_XLS = ".xls";

	/**
	 * 寻找当前目录寻找.xls文件
	 */
	public static File findExcelFile() {
		File dir = new File("./");
		System.out.println("dir exists : " + dir.exists());
		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			for (File file : files) {
				System.out.println("file : " + file.getName());
				if (file.getName().endsWith(FILE_XLS)) {
					return file;
				}
			}
			if (files != null && files.length > 0) {
				String[] contents = new String[files.length];
				for (int i = 0; i < files.length; i++) {
					contents[i] = files[i].getName();
				}
			}
		}
		return null;
	}
}
