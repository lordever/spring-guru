package com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.controllers

import com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.models.Beer
import com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.services.BeerService
import mu.KotlinLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
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
}