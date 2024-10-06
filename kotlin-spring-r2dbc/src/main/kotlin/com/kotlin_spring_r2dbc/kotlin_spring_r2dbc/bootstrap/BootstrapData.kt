package com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.bootstrap

import com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.domain.Beer
import com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.repositories.BeerRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class BootstrapData(
    var beerRepository: BeerRepository
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        loadBeerData()

        beerRepository.count().subscribe { count -> println("The count is $count") }
    }

    fun loadBeerData() {

        beerRepository.count().subscribe { count ->
            run {
                if (count == 0L) {
                    val beer1 = Beer(
                        name = "Galaxy Cat",
                        style = "Pale Ale",
                        upc = "12356",
                        price = BigDecimal("12.99"),
                        quantity = 122
                    )

                    val beer2 = Beer(
                        name = "Crank",
                        style = "Pale Ale",
                        upc = "12356222",
                        price = BigDecimal("11.99"),
                        quantity = 392
                    )

                    val beer3 = Beer(
                        name = "Sunshine City",
                        style = "IPA",
                        upc = "12356",
                        price = BigDecimal("13.99"),
                        quantity = 144
                    )

                    beerRepository.save(beer1).subscribe()
                    beerRepository.save(beer2).subscribe()
                    beerRepository.save(beer3).subscribe()
                }
            }
        }
    }
}