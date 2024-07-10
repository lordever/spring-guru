package com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.services

import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.models.Beer
import java.util.UUID

interface BeerService {
    fun getBeerById(id: UUID): Beer?
    fun listBeer(): List<Beer>
    fun save(beer: Beer): Beer
    fun updateById(id: UUID, newBeer: Beer)
    fun patchById(id: UUID, newBeer: Beer)
    fun deleteById(id: UUID)
}