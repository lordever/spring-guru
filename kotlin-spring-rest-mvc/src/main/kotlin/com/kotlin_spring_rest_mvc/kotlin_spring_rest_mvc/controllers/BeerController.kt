package com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.controllers

import com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.models.Beer
import com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.services.BeerService
import mu.KotlinLogging
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class BeerController(private val beerService: BeerService) {

    private val logger = KotlinLogging.logger {}

    @RequestMapping("/api/v1/beers")
    fun listBeers(): List<Beer> {
        return beerService.listBeer()
    }

    fun getBeerById(id: UUID): Beer? {
        logger.debug { "Get beer by id $id" }
        return beerService.getBeerById(id)
    }
}