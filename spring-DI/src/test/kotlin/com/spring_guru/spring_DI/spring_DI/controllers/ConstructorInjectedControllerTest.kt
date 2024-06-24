package com.spring_guru.spring_DI.spring_DI.controllers

import com.spring_guru.spring_DI.spring_DI.services.GreetingServiceImpl
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class ConstructorInjectedControllerTest {

    lateinit var constructorInjectedController: ConstructorInjectedController

    @BeforeEach
    fun setUp() {
        constructorInjectedController = ConstructorInjectedController(greetingService = GreetingServiceImpl())
    }

    @Test
    fun sayHello() {
        println(constructorInjectedController.sayHello())
    }
}