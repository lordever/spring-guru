package com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.controller

import com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.model.BeerDTO
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import java.math.BigDecimal

@RestController
class BeerController() {
    companion object {
        const val BEER_PATH = "/api/v2/beers"
    }

    @GetMapping(BEER_PATH)
    fun listBeers(): Flux<BeerDTO> {
        return Flux.just(
            BeerDTO(
                id = 1,
                name = "Happy Ale",
                style = "Ale",
                upc = "123456",
                price = BigDecimal.ONE,
                quantity = 10
            ),
            BeerDTO(
                id = 2,
                name = "Guiness",
                style = "Dark Beer",
                upc = "41235",
                price = BigDecimal.TEN,
                quantity = 10
            )
        )
    }
}