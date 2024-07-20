package com.kotlin_spring_mysql.kotlin_spring_mysql.controllers

import com.kotlin_spring_mysql.kotlin_spring_mysql.models.BeerDTO
import com.kotlin_spring_mysql.kotlin_spring_mysql.services.BeerService
import mu.KotlinLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
class BeerController(private val beerService: BeerService) {

    private val logger = KotlinLogging.logger {}

    companion object {
        const val BASE_BEER_PATH = "/api/v1/beers"
        const val BEER_PATH_WITH_ID = "$BASE_BEER_PATH/{id}"
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(): ResponseEntity<String> {
        println("Calling from BeerController custom exception handler")

        return ResponseEntity.notFound().build()
    }

    @GetMapping(BASE_BEER_PATH)
    fun listBeers(): List<BeerDTO> = beerService.listBeer()

    @GetMapping(BEER_PATH_WITH_ID)
    fun getBeerById(@PathVariable("id") id: UUID): BeerDTO? {
        logger.debug { "Get beer by id $id" }
        return beerService.getBeerById(id) ?: throw NotFoundException()
    }

    @PostMapping(BASE_BEER_PATH)
    fun handlePost(@Validated @RequestBody beerDTO: BeerDTO): ResponseEntity<BeerDTO> {
        val savedBeerDTO: BeerDTO = beerService.save(beerDTO)

        val headers = HttpHeaders()
        headers.add("Location", "/api/v1/beers/${savedBeerDTO.id}")

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .headers(headers)
            .body(savedBeerDTO)
    }

    @PutMapping(BEER_PATH_WITH_ID)
    fun updateById(@PathVariable id: UUID, @RequestBody beerDTO: BeerDTO): ResponseEntity<Void> {
        beerService.updateById(id, beerDTO) ?: throw NotFoundException()

        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @PatchMapping(BEER_PATH_WITH_ID)
    fun patchById(@PathVariable id: UUID, @Valid @RequestBody beerDTO: BeerDTO): ResponseEntity<Void> {
        beerService.patchById(id, beerDTO)?: throw NotFoundException()

        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @DeleteMapping(BEER_PATH_WITH_ID)
    fun deleteById(@PathVariable("id") id: UUID): ResponseEntity<Void> {
        if (!beerService.deleteById(id)) throw NotFoundException()

        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}