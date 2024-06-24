package com.spring_guru.spring_DI.spring_DI.controllers

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ConstructorInjectedControllerTest {

    @Autowired
    lateinit var constructorInjectedController: ConstructorInjectedController

    @Test
    fun sayHello() {
        println(constructorInjectedController.sayHello())
    }
}