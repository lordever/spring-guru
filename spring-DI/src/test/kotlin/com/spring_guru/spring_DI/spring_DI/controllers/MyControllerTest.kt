package com.spring_guru.spring_DI.spring_DI.controllers

import com.spring_guru.spring_DI.spring_DI.controllers.MyController
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MyControllerTest {
    @Test
    fun sayHello() {
        val myController = MyController()
        println(myController.sayHello())
    }
}