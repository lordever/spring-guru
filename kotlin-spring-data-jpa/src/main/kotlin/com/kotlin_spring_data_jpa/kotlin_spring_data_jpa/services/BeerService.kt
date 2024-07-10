package com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.services

import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.models.BeerDTO
import java.util.UUID

interface BeerService {
    fun getBeerById(id: UUID): BeerDTO?
    fun listBeer(): List<BeerDTO>
    fun save(beerDTO: BeerDTO): BeerDTO
    fun updateById(id: UUID, newBeerDTO: BeerDTO)
    fun patchById(id: UUID, newBeerDTO: BeerDTO)
    fun deleteById(id: UUID)
}