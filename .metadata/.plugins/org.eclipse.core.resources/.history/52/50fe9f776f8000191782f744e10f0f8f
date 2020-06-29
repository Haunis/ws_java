package com.expression.high

/*
 模仿with源码
 with没有T.with()函数
 
 作用： 操作一个对象，把对象作为参数传入；返回最后一行代码的对象
 
 */
//由于block是T的扩展函数，所以block可以使用上下文
fun <T, R> myWith(receiver: T, block: T.() -> R): R {
	return receiver.block()
//	return block(receiver)//两种写法都行
}

fun main(args: Array<String>) {
	val str = "abc"
	myWith(str, {
		println("this = ${this}")
		println("length = ${this.length}")
		println("this.first() = ${this.first()}")
		println("this.last() = ${this.last()}")
	})
	println("函数作为最后一个参数时，可在花括号\"{}\"内实现-----------------")
	val result = myWith(str) {
		println("this = ${this}")
		println("length = ${this.length}")
		println("this.first() = ${this.first()}")
		println("this.last() = ${this.last()}")
		val book = Book("Kotlin")
		book
	}
	println("result = ${result}")//book
}

