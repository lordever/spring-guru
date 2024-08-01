package com.kotlin_spring_mysql.kotlin_spring_mysql.repositories

import com.kotlin_spring_mysql.kotlin_spring_mysql.boostrap.BootstrapData
import com.kotlin_spring_mysql.kotlin_spring_mysql.entities.Beer
import com.kotlin_spring_mysql.kotlin_spring_mysql.models.BeerStyle
import com.kotlin_spring_mysql.kotlin_spring_mysql.services.BeerCsvServiceImpl
import jakarta.validation.ConstraintViolationException
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.springframework.context.annotation.Import
import java.math.BigDecimal

@DataJpaTest
@Import(BootstrapData::class, BeerCsvServiceImpl::class)
class BeerRepositoryTestKotlin {
    @Autowired
    lateinit var beerRepository: BeerRepository

    @Test
    fun testGetBeerListByName() {
        val list: List<Beer> = beerRepository.findAllByNameIsLikeIgnoreCase("%IPA%")

        assertThat(list).hasSize(336)
    }

    @Test
    fun testGetBeerListByStyle() {
        val list: List<Beer> = beerRepository.findAllByStyle(BeerStyle.ALE)

        assertThat(list).hasSize(400)
    }

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