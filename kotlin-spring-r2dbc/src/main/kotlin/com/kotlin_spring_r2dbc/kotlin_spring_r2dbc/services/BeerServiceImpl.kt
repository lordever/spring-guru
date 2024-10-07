package com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.services

import com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.mappers.BeerMapper
import com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.model.BeerDTO
import com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.repositories.BeerRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class BeerServiceImpl(
    var beerRepository: BeerRepository, var beerMapper: BeerMapper
) : BeerService {
    override fun listBeers(): Flux<BeerDTO> {
        return beerRepository.findAll().map(beerMapper::toDto)
    }

    override fun getBeerById(beerId: Int): Mono<BeerDTO> {
        return beerRepository.findById(beerId).map(beerMapper::toDto)
    }

    override fun createBeer(beerDTO: BeerDTO): Mono<BeerDTO> {
        return beerRepository.save(beerMapper.toBeer(beerDTO)).map(beerMapper::toDto)
    }

    override fun updateBeer(beerId: Int, dto: BeerDTO): Mono<BeerDTO> {
        return beerRepository.findById(beerId).map { foundBeer ->
            foundBeer.name = dto.name
            foundBeer.style = dto.style
            foundBeer.price = dto.price
            foundBeer.upc = dto.upc
            foundBeer.quantity = dto.quantity

            foundBeer
        }.flatMap(beerRepository::save).map(beerMapper::toDto)
    }

    override fun patchBeer(beerId: Int, dto: BeerDTO): Mono<BeerDTO> {
        return beerRepository.findById(beerId).map { foundBeer ->
            if (dto.name != null) foundBeer.name = dto.name
            if (dto.style != null) foundBeer.style = dto.style
            if (dto.price != null) foundBeer.price = dto.price
            if (dto.upc != null) foundBeer.upc = dto.upc
            if (dto.quantity != null) foundBeer.quantity = dto.quantity

            foundBeer
        }.flatMap(beerRepository::save).map(beerMapper::toDto)
    }
}