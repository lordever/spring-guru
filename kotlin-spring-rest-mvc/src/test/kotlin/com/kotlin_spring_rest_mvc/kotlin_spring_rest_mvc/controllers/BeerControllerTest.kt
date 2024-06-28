package com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.controllers

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
class BeerControllerTest {

    @Autowired
    lateinit var controller: BeerController
    @Test
    fun getBeerById() {
        println(controller.getBeerById(UUID.randomUUID()))
    }
}