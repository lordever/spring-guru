package com.spring_guru.spring_DI.spring_DI

import com.spring_guru.spring_DI.spring_DI.controllers.MyController
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext

@SpringBootApplication
class SpringDiApplication

fun main(args: Array<String>) {
	val ctx: ApplicationContext = runApplication<SpringDiApplication>(*args)

	val controller: MyController = ctx.getBean(MyController::class.java)

	println("I'm in main method")
	println(controller.sayHello())
}
