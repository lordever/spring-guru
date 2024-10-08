package com.kotlin_spring_security.kotlin_spring_security.repositories

import com.kotlin_spring_security.kotlin_spring_security.boostrap.BootstrapData
import com.kotlin_spring_security.kotlin_spring_security.entities.Beer
import com.kotlin_spring_security.kotlin_spring_security.models.BeerStyle
import com.kotlin_spring_security.kotlin_spring_security.services.BeerCsvServiceImpl
import jakarta.validation.ConstraintViolationException
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.springframework.context.annotation.Import
import org.springframework.data.domain.Page
import java.math.BigDecimal

@DataJpaTest
@Import(BootstrapData::class, BeerCsvServiceImpl::class)
class BeerRepositoryTestKotlin {
    @Autowired
    lateinit var beerRepository: BeerRepository

    @Test
    fun testGetBeerListByName() {
        val list: Page<Beer> = beerRepository.findAllByNameIsLikeIgnoreCase("%IPA%", null)

        assertThat(list).hasSize(336)
    }

    @Test
    fun testGetBeerListByStyle() {
        val list: Page<Beer> = beerRepository.findAllByStyle(BeerStyle.ALE, null)

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