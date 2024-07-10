package com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.controllers

import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.models.Beer
import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.services.BeerService
import mu.KotlinLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class BeerController(private val beerService: BeerService) {

    private val logger = KotlinLogging.logger {}

    companion object {
        const val BASE_BEER_PATH = "/api/v1/beers"
        const val BEER_PATH_WITH_ID = "$BASE_BEER_PATH/{id}"
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(): ResponseEntity<String>  {
        println("Calling from BeerController custom exception handler")

        return ResponseEntity.notFound().build()
    }

    @GetMapping(BASE_BEER_PATH)
    fun listBeers(): List<Beer> = beerService.listBeer()

    @GetMapping(BEER_PATH_WITH_ID)
    fun getBeerById(@PathVariable("id") id: UUID): Beer? {
        logger.debug { "Get beer by id $id" }
        return beerService.getBeerById(id) ?: throw NotFoundException()
    }

    @PostMapping(BASE_BEER_PATH)
    fun handlePost(@RequestBody beer: Beer): ResponseEntity<Beer> {
        val savedBeer: Beer = beerService.save(beer)

        val headers = HttpHeaders()
        headers.add("Location", "/api/v1/beers/${savedBeer.id}")

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .headers(headers)
            .body(savedBeer)
    }

    @PutMapping(BEER_PATH_WITH_ID)
    fun updateById(@PathVariable id: UUID, @RequestBody beer: Beer): ResponseEntity<Void> {
        beerService.updateById(id, beer)

        return ResponseEntity(HttpStatus.NO_CONTENT)

    }

    @PatchMapping(BEER_PATH_WITH_ID)
    fun patchById(@PathVariable id: UUID, @RequestBody beer: Beer): ResponseEntity<Void> {
        beerService.patchById(id, beer)

        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @DeleteMapping(BEER_PATH_WITH_ID)
    fun deleteById(@PathVariable("id") id: UUID): ResponseEntity<Void> {
        beerService.deleteById(id)

        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}