package com.delegate
/*
 lazy()是一个函数，接受一个lambda表达式作为参数，返回一个Lazy<T>实例函数
 */
fun foo() {
	println("foo execute")
}

val lazyValue: String by lazy {
	println("completed")//第一次调用输出，第二次调用不执行
	foo()
	"hello"
}

fun main(args: Array<String>) {
	println(lazyValue)//第一次执行，执行两次，输出表达式

	println("----------------------------------")
	println(lazyValue)//第二次执行，只输出返回值
}