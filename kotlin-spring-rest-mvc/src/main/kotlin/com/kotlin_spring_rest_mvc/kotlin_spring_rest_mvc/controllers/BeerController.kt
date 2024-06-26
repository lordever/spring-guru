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

    @GetMapping
    fun listBeers(): List<Beer> = beerService.listBeer()

    @GetMapping("/{id}")
    fun getBeerById(@PathVariable("id") id: UUID): Beer? {
        logger.debug { "Get beer by id $id" }
        return beerService.getBeerById(id)
    }

    @PostMapping
    fun handlePost(@RequestBody beer: Beer): ResponseEntity<Beer> {
        val savedBeer: Beer = beerService.save(beer)

        val headers = HttpHeaders()
        headers.add("Location", "/api/v1/beers/${savedBeer.id}")

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .headers(headers)
            .body(savedBeer)
    }

    @PutMapping("/{id}")
    fun updateById(@PathVariable id: UUID, @RequestBody beer: Beer): ResponseEntity<String> {
        beerService.updateById(id, beer)

        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body("Beer: $id was updated")
    }

    @PatchMapping("/{id}")
    fun patchById(@PathVariable id: UUID, @RequestBody beer: Beer): ResponseEntity<String> {
        beerService.patchById(id, beer)

        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body("Beer: $id was updated")
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable("id") id: UUID): ResponseEntity<String> {
        beerService.deleteById(id)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body("Beer: $id was deleted")
    }
}