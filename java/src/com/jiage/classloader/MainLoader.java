package com.jiage.classloader;

import java.io.File;
import java.util.StringTokenizer;

/**
 * https://blog.csdn.net/javazejian/article/details/73413292
 * 
 * ClassLoader采用双亲委派模式：自定义加载器->AppClassLoader->ExtClassLoader/PlatformClassLoader->启动类加载器
 * 如果父加载器无法完成加载任务，就交给子加载器
 * ExtClassLoader/PlatformClassLoader父加载器为null,但却可以委托启动类加载器？
 * AppClassLoader和ExtClassLoader虽然均继承UrlClassLoader,但AppClassLoader的父加载器是ExtClassLoader
 * 
 * 采用双亲委派模式有点：1.避免重复加载 2.安全考虑，java核心api中定义类型不会被随意替换
 * 
 * 
 * 自定义加载器： 1.继承ClassLoader,重写findClass(); 2.继承UrlClassLoader,不必重写findClass()方法
 * 但无论继承ClassLoader还是继承UrlClassLoader，其父加载器均是APPClassLoader
 * 
 * 自定义类加载器的目的：
 * 1.当class文件不在ClassPath路径下，默认系统类加载器无法找到该class文件，在这种情况下我们需要实现一个自定义的ClassLoader
 * 来加载特定路径下的class文件生成class对象。
 * 2.当一个class文件是通过网络传输并且可能会进行相应的加密操作时，需要先对class文件进行相应的解密后再加载到JVM内存中，
 * 这种情况下也需要编写自定义的ClassLoader并实现相应的逻辑。
 * 3.当需要实现热部署功能时(一个class文件通过不同的类加载器产生不同class对象从而实现热部署功能)，需要实现自定义ClassLoader的逻辑。
 * 
 * 并不是所有的加载流程都遵循双亲委派模式：线程上下文类加载器
 * JVM在加载核心接口如rt.jar中的接口时，使用的是启动类加载器；但此接口的实现是在外部的jar中实现，所以不能使用启动类加载器加载，
 * 又由于双亲委派模式的存在，所以Bootstrap启动类加载器又不能反向委托AppClassLoader加载。这个时候就使用到了线程上下文加载器
 *
 */
public class MainLoader {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		File[] files = getExtDirs();
		System.out.println("ExtClassLoader： " + files.length);
		
		System.out.println("父加载器--------------------------------------------------------");
		ClassLoader systemLoader = ClassLoader.getSystemClassLoader();
		ClassLoader systemLoalderParent = systemLoader.getParent();
		ClassLoader systemLoalderParentParent = systemLoalderParent.getParent();
		System.out.println("systemLoader : " + systemLoader);// AppClassLoader
		System.out.println("systemLoalderParent : " + systemLoalderParent);// PlatformClassLoader,1.8是ExtClassLoader
		System.out.println("systemLoalderParentParent : " + systemLoalderParentParent);// null

		System.out.println("自定义类加载器-------------------------------------------------");
		FileClassLoader loader = new FileClassLoader();
		System.out.println("自定义加载器的父类加载器：" + loader.getParent());// AppClassLoader
		try {
			Class<?> clazz = loader.findClass("com.jiage.classloader.LoaderBean");
//			Class<?> clazz = loader.loadClass("com.jiage.classloader.LoaderBean");//不会走findClass方法。。。
			System.out.println(clazz.newInstance().toString());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	// ExtClassLoader加载的文件
	private static File[] getExtDirs() {
		// 加载<JAVA_HOME>/lib/ext目录中的类库
		String s = System.getProperty("java.ext.dirs");
		File[] dirs;
		if (s != null) {
			StringTokenizer st = new StringTokenizer(s, File.pathSeparator);
			int count = st.countTokens();
			dirs = new File[count];
			for (int i = 0; i < count; i++) {
				dirs[i] = new File(st.nextToken());
			}
		} else {
			dirs = new File[0];
		}
		return dirs;
	}
}
