package com.object_declarations

/*
 一，对象表达式：相当于匿名类
 对象表达式语法：1.object:A(){} 2.越过类的定义直接得到一个对象 object {var name = "lee"}--可用作函数返回值
 用处：
 1.用作某个函数的入参,作为匿名内部类使用 fun foo(object:A(){}){}
 2.直接赋值给某个变量：val a:A = object :A(){}
 匿名对象只可以用在本地和私有作用域。如果匿名对象作为公有函数的返回类型或者用作公有属性的话，那么该函数或者属性
 的实际类型是匿名对象的超类型--如果没有声明任何超类型就默认是Any，在匿名对象中添加的成员将无法访问
 
 
 二，对象声明：
 语法：object A{}
 与对象表达式相比，要带声明的类名A
 好处：获取单例方便；【【此类下的方法都是静态方法】】
 
 三，伴生对象：
 一个类里面值能有一个伴生对象。伴生对象里的方法相当于静态方法
 
 */

//1.对象表达式-----------------------------------------------------------
interface TestInterface {
	fun eat()
}

open class Person(age: Int) {
	open val AGE = age;
	//越过类的定义，直接得到一个对象。匿名对象用作私有函数的返回值
	private fun privateFun() = object {
		val x: String = "privateFun"
	}

	public fun publicFun() = object {
		val x: String = "publicFun"
	}

	open fun foo() {
		val pf = privateFun()
		println("pf=${pf}, pf.x = ${pf.x}")
		println("privateFun().x = ${privateFun().x}")
//		println(publicFun().x)//error  匿名对象只可以用在本地和私有作用域
	}
}

val PersonExpression: Person = object : Person(17), TestInterface {
	override val AGE = 15;
	override fun foo() {
		super.foo()
		println("PersonExpression foo()")
	}

	override fun eat() {
		println("PersonExpression eat()")
	}

	fun expressionFoo() {

	}
}

//匿名内部类测试
class Student {
	fun test(testInterface: TestInterface) {
		testInterface.eat()
	}

	fun test2(person: Person) {
		person.foo()
	}
}

//2.对象声明-----------------------------------------------------------
object Site {
	var url: String = ""
	var name: String = "Baidu"
	fun foo() {
		println("Site foo excuted")
	}
}

class Outer {
	val outerName = "Outer Lee"

	object ObjectInner {
		val objectInnerName = "Inner Lee"
		fun showName(): Unit {
//			println("${outerName}")//error,无法访问外部类的方法和变量
//			val outer: Outer = this@Outer//不可以引用外部类
			val outer: Outer = Outer()//可以实例化外部类
			println("showName(): ${outer.outerName}")
		}
	}
}

//3.伴生对象-----------------------------------------------------------
class Animal {
	val age: Int = 7

	companion object Cat {
		fun create(): Animal = Animal()
		fun foo() {
//			println("${age}")//不可以直接访问外部类的成员
			println("foo() cat run")
		}
	}
//	companion object dot{}//error,一个类里面只可以有一个伴生对象
}

class Fruit {
	//可以省略伴生对象的对象名，实例化时用Copanion代替
	companion object {
		val name: String = "apple"
	}
}

fun Fruit.Companion.eat() {}

//fun Animal.Companion.eat() {}//伴随对象有名字的不可以这样扩展方法
fun Animal.Cat.eat() {}

fun main(args: Array<String>) {
	println("1.对象表达式--------------------------------------")
//	val personExpression = PersonExpression()//erron;不可以这样实例化
	println("PersonExpression.age=${PersonExpression.AGE}")
	PersonExpression.foo()//
//	PersonExpression.expressionFoo();//error;
//	PersonExpression.eat()//error

	//越过类的定义直接得到对象
	val test = object {
		val age = 33
		val name = "lee"
		fun foo() {
			println("test foo()")
		}
	}
	println("test = ${test} , test.name=${test.name}")
	test.foo()

	val stu = Student()
	stu.test(object : TestInterface {
		override fun eat() {
			println("匿名函数：just eat ")
		}
	})
//	stu.test { (???) -> println("just lamebda") }
	stu.test2(object : Person(17) {
		override fun foo() {
			println("匿名函数:just foo")
		}
	})

	println("2.对象声明--------------------------------------")
	//默认就是单例
	val site1 = Site
	val site2 = Site
	site1.url = "www.baidu.com"
	println("site1.url = ${site1.url}")
	println("site2.url = ${site2.url}")
	Site.foo()//对象声明里的都是静态---可以这样理解

	val outer: Outer = Outer()
//	val innerName = outer.ObjectInner//不能通过外部的实例访问内部的对象声明
	val innerName = Outer.ObjectInner.objectInnerName
	println("innerName = ${innerName}")

	Site.foo()//对象声明里的都是静态---可以这样理解

	println("3.伴生对象--------------------------------------")
	val cat = Animal.Cat
	cat.foo()
	Animal.foo()//外部类可以直接使用伴生对象的成员
	val animal = Animal()
//	animal.foo()//error ,不可以实例调用，只能类名调用；相当于静态

	println("Fruit.name = ${Fruit.name}")
}

















