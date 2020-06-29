package com.expression.high

/*
 模仿run源码
 
 run作用：执行lambda表达式,返回执行结果
 T.run()作用：可以使用上下文T,执行lambda表达式，返回执行结果
 */
fun <R> myRun(block: () -> R): R {
	return block()
}

//由于block是T的扩展函数，所以block可以使用上下文
fun <T, R> T.myRun(block: T.() -> R): R {
	return block()
}

fun main(args: Array<String>) {
	println("myRun{}---------------------------------------------")
	myRun({
		->
		println("this is myRun")
		val book = Book("Kotlin")
		book
	})
	println()
	//函数作为最后一个参数时，可在花括号"{}"内实现
	val result = myRun {
		//可省略"->"
		->
		println("this is myRun")
		val book = Book("Kotlin")
		book
	}
	println("myRun result = ${result}")//book

	println("T.myRun()---------------------------------------------")
	val str = "abc"
	val result2_1 = str.myRun({
		->
		println("this is T.myRun() ,this = ${this}")
		val book = Book("kotlin")
		book
	})
	println("result2_1 = ${result2_1}")
	println("")
	val result2_2 = str.myRun {
		//可以省略"->"
		->
		println("this is T.myRun() ,this = ${this}")
		val book = Book("kotlin")
		book
	}
	println("result2_2 = ${result2_2}")
}




