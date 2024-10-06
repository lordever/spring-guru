package com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.controller

import com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.model.BeerDTO
import com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.services.BeerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
class BeerController(var beerService: BeerService) {
    companion object {
        const val BEER_PATH = "/api/v2/beers"
        const val BEER_PATH_ID = "$BEER_PATH/{beerId}"
    }

    @GetMapping(BEER_PATH)
    fun listBeers(): Flux<BeerDTO> {
        return beerService.listBeers()
    }

    @GetMapping(BEER_PATH_ID)
    fun getBeerById(@PathVariable beerId: Int): Mono<BeerDTO> {
        return beerService.getBeerById(beerId)
    }

    @PostMapping(BEER_PATH)
    fun createNewBeer(@RequestBody beerDTO: BeerDTO): Mono<ResponseEntity<Void>> {
        return beerService.createBeer(beerDTO)
            .map { savedDto ->
                ResponseEntity.created(
                    UriComponentsBuilder
                        .fromHttpUrl("http://localhost:8080/${BEER_PATH}/${savedDto.id}")
                        .build()
                        .toUri()
                ).build()
            }
    }
}