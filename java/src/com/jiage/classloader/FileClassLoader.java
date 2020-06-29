package com.jiage.classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class FileClassLoader extends ClassLoader {
	private String rootDir = "/home/haunis/Workspace_Java/Java/bin";

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		byte[] classData = getClassData(name);
		if (classData == null) {
			throw new ClassNotFoundException();
		} else {
			return defineClass(name, classData, 0, classData.length);
		}
	}

	private byte[] getClassData(String className) {
		try {
			String classPath = getClassPath(className);
			System.out.println("classPath: " + classPath);// -----------------------------------------
			InputStream ins = new FileInputStream(classPath);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int buffSize = 4096;
			byte[] buffer = new byte[buffSize];
			int bytesNumRead = 0;
			while ((bytesNumRead = ins.read(buffer)) != -1) {
				bos.write(buffer, 0, bytesNumRead);
			}
			ins.close();
			bos.close();
			return bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String getClassPath(String className) {
		return rootDir + File.separatorChar + className.replace(".", File.separator) + ".class";
	}
}
