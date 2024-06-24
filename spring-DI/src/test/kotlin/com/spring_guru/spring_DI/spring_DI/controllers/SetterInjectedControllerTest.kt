package com.spring_guru.spring_DI.spring_DI.controllers

import com.spring_guru.spring_DI.spring_DI.services.GreetingServiceImpl
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SetterInjectedControllerTest {
    @Autowired
    lateinit var setterInjectedController: SetterInjectedController

    @Test
    fun sayHello() {
        println(setterInjectedController.sayHello())
    }
}