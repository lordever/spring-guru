package com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.controllers

import com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.models.Beer
import com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.services.BeerService
import mu.KotlinLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/beers")
class BeerController(private val beerService: BeerService) {

    private val logger = KotlinLogging.logger {}


    @PostMapping
    fun handlePost(@RequestBody beer: Beer): ResponseEntity<Beer> {
        val savedBeer: Beer = beerService.saveNewBeer(beer)

        val headers = HttpHeaders()
        headers.add("Location", "/api/v1/beers/${savedBeer.id}")

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .headers(headers)
            .body(savedBeer)
    }

    @GetMapping
    fun listBeers(): List<Beer> = beerService.listBeer()

    @GetMapping("/{id}")
    fun getBeerById(@PathVariable("id") id: UUID): Beer? {
        logger.debug { "Get beer by id $id" }
        return beerService.getBeerById(id)
    }
}