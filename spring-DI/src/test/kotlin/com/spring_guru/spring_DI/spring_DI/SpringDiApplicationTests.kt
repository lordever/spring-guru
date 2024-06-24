package com.spring_guru.spring_DI.spring_DI

import com.spring_guru.spring_DI.spring_DI.controllers.MyController
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext

@SpringBootTest
class SpringDiApplicationTests {

	@Autowired
	lateinit var ctx: ApplicationContext

	@Autowired
	lateinit var myController: MyController

	@Test
	fun testGetControllerFromCtx() {
		val controller: MyController = ctx.getBean(MyController::class.java)
		println(controller.sayHello())
	}

	@Test
	fun testAutowiredOfController() {
		println(myController.sayHello())
	}

	@Test
	fun contextLoads() {
	}

}
