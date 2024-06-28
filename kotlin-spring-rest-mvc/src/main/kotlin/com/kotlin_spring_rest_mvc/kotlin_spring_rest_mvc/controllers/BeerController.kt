package com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.controllers

import com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.models.Beer
import com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.services.BeerService
import mu.KotlinLogging
import org.springframework.stereotype.Controller
import java.util.UUID

@Controller
class BeerController(private val beerService: BeerService) {

    private val logger = KotlinLogging.logger {}

    fun getBeerById(id: UUID): Beer {
        logger.debug { "Get beer by id $id" }
        return beerService.getBeerById(id)
    }
}