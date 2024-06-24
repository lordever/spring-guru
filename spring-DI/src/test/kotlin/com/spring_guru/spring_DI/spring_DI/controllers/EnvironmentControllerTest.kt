package com.spring_guru.spring_DI.spring_DI.controllers

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

//Possible profiles - qa, dev, prod
@ActiveProfiles(value = ["qa", "ES"])
@SpringBootTest
class EnvironmentControllerTest {

    @Autowired
    private lateinit var environmentController: EnvironmentController

    @Test
    fun sayHello() {
        println(environmentController.sayHello())
    }
}