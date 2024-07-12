package com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.repositories

import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.entities.Beer
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

import org.assertj.core.api.Assertions.assertThat

@DataJpaTest
class BeerRepositoryTestKotlin {
    @Autowired
    lateinit var beerRepository: BeerRepository


    @Test
    fun testSaveBer() {
        val beer = Beer(
            name = "Test Beer"
        )

        val savedBeer = beerRepository.save(beer)

        assertThat(savedBeer).isNotNull
        assertThat(savedBeer.id).isNotNull
        assertThat(savedBeer.name).isEqualTo(beer.name)
    }

}