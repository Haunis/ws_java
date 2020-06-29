package com.delegate

import kotlin.properties.Delegates

/**
适用无法在初始化就确定属性值的情形
如果属性未被赋值就去访问，会报异常
 */
class Book {
	var notNullString: String by Delegates.notNull<String>()
}

fun main(args: Array<String>) {
	val book = Book()
//	println("book.noNullString = ${book.notNullString}")//未赋值会报错，java.lang.IllegalStateException
	book.notNullString = "bar"
	println("book.noNullString = ${book.notNullString}")
}
