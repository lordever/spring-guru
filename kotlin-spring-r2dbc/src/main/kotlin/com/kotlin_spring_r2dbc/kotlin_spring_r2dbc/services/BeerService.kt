package com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.services

import com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.model.BeerDTO
import org.springframework.http.ResponseEntity
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface BeerService {
    fun listBeers(): Flux<BeerDTO>
    fun getBeerById(beerId: Int): Mono<BeerDTO>
    fun createBeer(beerDTO: BeerDTO): Mono<BeerDTO>
}