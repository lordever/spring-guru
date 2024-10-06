package com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.controller

import com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.model.BeerDTO
import com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.services.BeerService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
class BeerController(var beerService: BeerService) {
    companion object {
        const val BEER_PATH = "/api/v2/beers"
    }

    @GetMapping(BEER_PATH)
    fun listBeers(): Flux<BeerDTO> {
        return beerService.listBeers()
    }
}