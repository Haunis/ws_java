package com.jiage.fanxing;

import java.util.ArrayList;
import java.util.List;

/**
 * 参考： http://www.importnew.com/24029.html
 * 
 * 通配符：使用在类的后面，如：List<? extends Fruit>
 * 
 * 边界符：使用在方法返回值前面，如： private <T extends Fruit> void getFruit(List<T> list){}
 * 
 * 类型擦除的处理： 使用边界符
 * 
 * 因为泛型擦除的存在，所以要注意以下问题： 1. java里没有泛型数组
 * 
 * 要点：1.多态 2.List<Parent> list 可以添加Parent的子类，但不能反过来. 从这两点分析，就可以分析出下文所有结论
 * 
 *
 */
public class Amain<T> {
	public static void main(String[] args) {
		Float[] array = new Float[] { 1f, 2f, 3f, 4f, 5f };
		Utils.printArray(array);// 卧槽。。。必须使用引用类型才可以

		List<Apple> appleList = new ArrayList<Apple>();
		List<Orange> orangeList = new ArrayList<Orange>();
		List<Fruit> fruitList = new ArrayList<Fruit>();

		Fruit fruit0, fruit2, fruit3;
		Utils<Fruit> utilsFruit = new Utils<Fruit>();
		Utils<Apple> utilsApple = new Utils<Apple>();
//		Utils utils = new Utils();

		System.out.println("List<Fruit>可以添加Apple,Orange,Fruit------------------------");
		fruitList.add(new Apple());
		fruitList.add(new Orange());
		fruitList.add(new Fruit());
		System.out.println("fruitList0 : " + fruitList.get(0));
		System.out.println("fruitList1 : " + fruitList.get(1));
		System.out.println("fruitList2 : " + fruitList.get(2));

		System.out.println("List<Apple>只可以添加Apple-----------------------------------");
		appleList.add(new Apple());
//		appleList.add((Apple) new Fruit());//运行时报错，ClassCastExeception
		System.out.println("appleList0 : " + appleList.get(0));

		System.out.println("fruitList使用泛型方法---------------------------------------------");
		fruit0 = utilsFruit.getFirst(fruitList);
		fruit2 = utilsFruit.getFirst2(fruitList);// 使用通配符即可
		fruit3 = utilsFruit.getFirst3(fruitList);// 使用边界符亦可
		System.out.println("fruit0=" + fruit0 + "\nfruit2=" + fruit2 + "\nfruit3=" + fruit3);

		System.out.println("appleList使用泛型方法---------------------------------------------");
//		fruit0 = (Fruit) utils.getFirst(appleList);// 编译通不过
		fruit2 = utilsFruit.getFirst2(appleList);// 使用通配符即可
		fruit3 = utilsFruit.getFirst3(appleList);// 使用边界符亦可
		System.out.println("fruit2=" + fruit2 + "\nfruit3=" + fruit3);

		System.out.println("<? extends Fruit> 只能get ，不能add ---------------------------");
		// List<? extends Fruit>有以下三种含义,所以只能get，不能add
		List<? extends Fruit> listExtends0 = new ArrayList<Apple>();
		List<? extends Fruit> listExtends1 = new ArrayList<Orange>();
		List<? extends Fruit> listExtends2 = new ArrayList<Fruit>();

//		listExtends0.add(new Apple());//编译不过,不能add****************
//		listExtends2.add(new Fruit());//编译不过,不能add****************
		listExtends0.add(null);
		listExtends0.get(0);// 可以get****************

//		utilsFruit.put(listExtends, new Apple());//编译不过
//		utilsFruit.put2(listExtends, new Apple());//编译不过
//		utilsFruit.put(listExtends2, new Fruit());//编译不过
//		utilsFruit.put2(listExtends2, new Fruit());//编译不过
//		utilsFruit.getFirst(listExtends0);//编译不过
		utilsFruit.getFirst2(listExtends0);// 数组中无元素
		utilsFruit.getFirst3(listExtends0);// 数组中无元素

		System.out.println("<? super Apple>不能get，只能add ---------------------------------------------");
		// List<? super Apple>有以下三种含义,所以不能get,只能add--具体的Apple
		List<? super Apple> listSuper0 = new ArrayList<Apple>();
		List<? super Apple> listSuper1 = new ArrayList<Fruit>();
		List<? super Apple> listSuper2 = new ArrayList<Object>();

		listSuper0.add(new Apple());// 可以add具体的子类****************
//		listSuper0.add(new Fruit());//编译不过,不可以add父类****************
//		listSuper0.add(new Object());//编译不过，不可以add父类****************

		fruit0 = (Apple) listSuper0.get(0);// ****************需要强转才可以get

//		utilsFruit.put(listSuper, new Fruit());// 编译不过
//		utilsFruit.put(listSuper, new Apple());// 编译不过
//		utilsFruit.put(listSuper, new Orange());// 编译不过
//		utilsFruit.put2(listSuper, new Fruit());// 编译不过
//		utilsFruit.put2(listSuper, new Apple());// 编译不过
//		utilsFruit.put2(listSuper, new Orange());// 编译不过
		utilsApple.put2(listSuper0, new Apple());
//		utilsApple.put2(listSuper0, new Fruit());// 编译不过
		utilsApple.put2(fruitList, new Apple());
		utilsFruit.put2(fruitList, new Apple());// 之所以参数可以接受Apple，是因为多态

//		utils.getFirst(listSuper0);//编译不过
//		utils.getFirst2(listSuper0);//编译不过
//		utils.getFirst3(listSuper0);//编译不过

		// java里没有泛型数组
//		List<Integer>[] arrayOfLists = new List<Integer>[2]; 

		Object[] strings = new String[2];
		strings[0] = "a";
//		strings[1]=2;

	}
}
