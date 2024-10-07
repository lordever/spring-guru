package com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.controller

import com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.model.BeerDTO
import com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.services.BeerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
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

    @PutMapping(BEER_PATH_ID)
    fun updateExistingBeer(
        @PathVariable beerId: Int,
        @RequestBody dto: BeerDTO
    ): Mono<ResponseEntity<Void>> {
        return beerService.updateBeer(beerId, dto)
            .map { ResponseEntity.ok().build() }
    }

    @PatchMapping(BEER_PATH_ID)
    fun patchExistingBeer(
        @PathVariable beerId: Int,
        @RequestBody dto: BeerDTO
    ): Mono<ResponseEntity<Void>> {
        return beerService.patchBeer(beerId, dto)
            .map { ResponseEntity.ok().build() }
    }
}