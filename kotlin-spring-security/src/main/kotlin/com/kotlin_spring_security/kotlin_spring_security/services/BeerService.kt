package com.kotlin_spring_security.kotlin_spring_security.services

import com.kotlin_spring_security.kotlin_spring_security.models.BeerDTO
import com.kotlin_spring_security.kotlin_spring_security.models.BeerStyle
import org.springframework.data.domain.Page
import java.util.UUID

interface BeerService {
    fun getBeerById(id: UUID): BeerDTO?
    fun listBeer(
        name: String?,
        style: BeerStyle?,
        showInventory: Boolean = false,
        pageNumber: Int?,
        pageSize: Int?
    ): Page<BeerDTO>
    fun save(beerDTO: BeerDTO): BeerDTO
    fun updateById(id: UUID, newBeerDTO: BeerDTO): BeerDTO?
    fun patchById(id: UUID, newBeerDTO: BeerDTO): BeerDTO?
    fun deleteById(id: UUID): Boolean
}