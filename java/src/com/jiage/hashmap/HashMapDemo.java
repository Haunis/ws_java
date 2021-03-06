package com.jiage.hashmap;

import java.lang.reflect.Field;
import java.util.HashMap;
/**
 *	HashMap扩容原理分析：
 *	初始数组长度为16，默认加载因子是0.75；所以扩张极限是16*0.75=12
 *	HashMap容量达到12时，数组扩张两倍16*2=32，扩张极限亦扩张两倍12*2=24
 *	以后数组容量和扩张极限都是按2倍扩张
 *
 *	为什么加载因子是0.75？  https://blog.csdn.net/hcmony/article/details/56494527
 *	加载因子越大，填满的元素越多，空间利用率越高，但冲突机会加大了，查找成本高就高了
 *	加载因子越小，填满的元素越少，空间利用率越低，但冲突机会减少了，查找成本降低
 *	0.75是空间成本和时间成本的综合考量。
 *	
 *	当加载因子是0.75时，链长为8的概率已经很小了0.00000006亿分之六
 *	JAVA1.8之后做了改进，当链长大于8时，采用红黑树结构，以加快检索
 *
 */
public class HashMapDemo {
	public static void main(String[] args) {
		try {
			HashMap<Integer, String> map = new HashMap<>();
//			HashMap<Integer, String> map = new HashMap<>(1,2);
			Class clazz = map.getClass();
			Field threshold = null;
			Field table = null;
			Field[] fields = clazz.getDeclaredFields();

			for (Field field : fields) {
				field.setAccessible(true);
				if (field.getName().equals("threshold")) {
					threshold = field;
				}
				if (field.getName().equals("table")) {
					table = field;
				}
			}
			Object[] tables0 = (Object[]) table.get(map);
			System.out.println("init threshold = " + threshold.get(map) + "  table.length = " );

			
			for (int i = 0; i < 100; i++) {
				map.put(i, i + "");
				Object[] tables = (Object[]) table.get(map);
				System.out.println("threshold = " + threshold.get(map) + "  table.length = " + tables.length);
			}
	
		} catch (Exception e) {
			e.printStackTrace();
//			System.out.println(e.toString());
		}
	}
}
