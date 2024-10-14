package com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.controller

import com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.model.BeerDTO
import com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.services.BeerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
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
            .switchIfEmpty(Mono.error(ResponseStatusException(HttpStatus.NOT_FOUND)))
    }

    @PostMapping(BEER_PATH)
    fun createNewBeer(@Validated @RequestBody beerDTO: BeerDTO): Mono<ResponseEntity<Void>> {
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
        @Validated @RequestBody dto: BeerDTO
    ): Mono<ResponseEntity<Void>> {
        return beerService.updateBeer(beerId, dto)
            .switchIfEmpty(Mono.error(ResponseStatusException(HttpStatus.NOT_FOUND)))
            .map { ResponseEntity.noContent().build() }
    }

    @PatchMapping(BEER_PATH_ID)
    fun patchExistingBeer(
        @PathVariable beerId: Int,
        @Validated @RequestBody dto: BeerDTO
    ): Mono<ResponseEntity<Void>> {
        return beerService.patchBeer(beerId, dto)
            .switchIfEmpty(Mono.error(ResponseStatusException(HttpStatus.NOT_FOUND)))
            .map { ResponseEntity.ok().build() }
    }

    @DeleteMapping(BEER_PATH_ID)
    fun deleteById(@PathVariable beerId: Int): Mono<ResponseEntity<Void>> {
        return beerService.getBeerById(beerId)
            .switchIfEmpty(Mono.error(ResponseStatusException(HttpStatus.NOT_FOUND)))
            .mapNotNull { beerDto -> beerDto.id?.let { beerService.deleteBeerById(it) } }
            .then(Mono.fromCallable { ResponseEntity.noContent().build() })
    }
}