package com.kotlin_spring_mysql.kotlin_spring_mysql.repositories

import com.kotlin_spring_mysql.kotlin_spring_mysql.entities.Beer
import com.kotlin_spring_mysql.kotlin_spring_mysql.models.BeerStyle
import jakarta.validation.ConstraintViolationException
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import java.math.BigDecimal

@DataJpaTest
class BeerRepositoryTestKotlin {
    @Autowired
    lateinit var beerRepository: BeerRepository


    @Test
    fun testSaveBer() {
        val beer = Beer(
            name = "Test Beer",
            style = BeerStyle.ALE,
            upc = "Test UPC",
            price = BigDecimal(20.0),
        )

        val savedBeer = beerRepository.save(beer)

        assertThat(savedBeer).isNotNull
        assertThat(savedBeer.id).isNotNull
        assertThat(savedBeer.name).isEqualTo(beer.name)
    }

    @Test
    fun testSaveBerWhereNameIsTooLong() {
        assertThrows(ConstraintViolationException::class.java) {
            val beer = Beer(
                name = "Test Beer Test Beer Test Beer Test Beer Test Beer Test Beer",
                style = BeerStyle.ALE,
                upc = "Test UPC",
                price = BigDecimal(20.0),
            )

            val savedBeer = beerRepository.save(beer)
            beerRepository.flush()

            assertThat(savedBeer).isNotNull
            assertThat(savedBeer.id).isNotNull
            assertThat(savedBeer.name).isEqualTo(beer.name)
        }
    }

}