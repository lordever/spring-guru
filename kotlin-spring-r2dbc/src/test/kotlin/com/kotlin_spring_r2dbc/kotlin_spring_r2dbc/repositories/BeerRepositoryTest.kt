package com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.repositories

import com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.config.DatabaseConfig
import com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.domain.Beer
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest
import org.springframework.context.annotation.Import
import java.math.BigDecimal

@DataR2dbcTest
@Import(DatabaseConfig::class)
class BeerRepositoryTest {
    @Autowired
    lateinit var beerRepository: BeerRepository


    @Test
    fun saveNewBeer() {
        beerRepository.save(getTestBeer())
            .subscribe { beer -> println(beer.toString()) }
    }

    fun getTestBeer(): Beer =
        Beer(
            name = "Space Dust",
            style = "IPA",
            price = BigDecimal.TEN,
            quantity = 12,
            upc = "123213"
        )
}