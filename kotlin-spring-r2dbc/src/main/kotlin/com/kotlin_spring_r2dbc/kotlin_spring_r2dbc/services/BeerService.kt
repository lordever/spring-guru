package com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.services

import com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.model.BeerDTO
import reactor.core.publisher.Flux

interface BeerService {
    fun listBeers(): Flux<BeerDTO>
}