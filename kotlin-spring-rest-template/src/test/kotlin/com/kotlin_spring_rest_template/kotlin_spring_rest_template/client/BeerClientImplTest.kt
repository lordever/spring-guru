package com.kotlin_spring_rest_template.kotlin_spring_rest_template.client

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BeerClientImplTest {

    @Autowired
    lateinit var beerClient: BeerClientImpl

    @Test
    fun listBeers() {
        beerClient.listBeers()
    }
}