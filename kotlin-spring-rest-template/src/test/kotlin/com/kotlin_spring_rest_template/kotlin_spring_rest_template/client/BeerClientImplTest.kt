package com.kotlin_spring_rest_template.kotlin_spring_rest_template.client

import org.assertj.core.api.Assertions.assertThat
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
        beerClient.listBeers(null)
    }

    @Test
    fun listBeersByName() {
        val name = "ALE"
        val result = beerClient.listBeers(name)

        assertThat(result).isNotNull
        assertThat(result).isNotEmpty

        result?.content?.forEach { beer -> assertThat(beer.name).containsIgnoringCase(name) }
    }
}