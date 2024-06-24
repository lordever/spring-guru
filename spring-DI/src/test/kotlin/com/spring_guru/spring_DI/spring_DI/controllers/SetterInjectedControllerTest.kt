package com.spring_guru.spring_DI.spring_DI.controllers

import com.spring_guru.spring_DI.spring_DI.services.GreetingServiceImpl
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class SetterInjectedControllerTest {
    lateinit var setterInjectedController: SetterInjectedController

    @BeforeEach
    fun setUp() {
        setterInjectedController = SetterInjectedController()
        setterInjectedController.setService(GreetingServiceImpl()) //If it is commented exist, then NPE will be thrown
    }

    @Test
    fun sayHello() {
        println(setterInjectedController.sayHello())
    }
}