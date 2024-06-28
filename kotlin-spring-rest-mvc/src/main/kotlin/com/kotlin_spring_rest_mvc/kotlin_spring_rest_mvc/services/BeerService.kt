package com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.services

import com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.models.Beer
import java.util.UUID

interface BeerService {
    fun getBeerById(id: UUID): Beer?
    fun listBeer(): List<Beer>
}