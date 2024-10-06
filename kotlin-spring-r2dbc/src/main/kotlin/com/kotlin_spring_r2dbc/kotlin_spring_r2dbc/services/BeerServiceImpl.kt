package com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.services

import com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.mappers.BeerMapper
import com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.model.BeerDTO
import com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.repositories.BeerRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

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

    override fun getBeerById(beerId: Int): Mono<BeerDTO> {
        return beerRepository
            .findById(beerId)
            .map(beerMapper::toDto)
    }

    override fun createBeer(beerDTO: BeerDTO): Mono<BeerDTO> {
        return beerRepository
            .save(beerMapper.toBeer(beerDTO))
            .map(beerMapper::toDto)
    }
}