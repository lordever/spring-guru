package com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.services

import com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.mappers.BeerMapper
import com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.model.BeerDTO
import com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.repositories.BeerRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class BeerServiceImpl(
    var beerRepository: BeerRepository,
    var beerMapper: BeerMapper
) : BeerService {
    override fun listBeers(): Flux<BeerDTO> {
        return beerRepository
            .findAll()
            .map(beerMapper::toDto)
    }
}