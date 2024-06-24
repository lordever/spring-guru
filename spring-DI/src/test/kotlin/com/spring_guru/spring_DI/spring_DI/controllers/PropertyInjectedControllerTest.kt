package com.spring_guru.spring_DI.spring_DI.controllers

import com.spring_guru.spring_DI.spring_DI.services.GreetingServiceImpl
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class PropertyInjectedControllerTest {

    lateinit var propertyInjectedController: PropertyInjectedController

    @BeforeEach
    fun setUp() {
        propertyInjectedController = PropertyInjectedController()
        propertyInjectedController.greetingService = GreetingServiceImpl()
    }

    @Test
    fun sayHello() {
        println(propertyInjectedController.sayHello())
    }
}