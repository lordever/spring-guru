package com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.services

import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.mappers.BeerMapper
import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.models.BeerDTO
import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.repositories.BeerRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import java.util.*

@Primary
@Service
class BeerServiceJpaImpl : BeerService {
    private lateinit var beerRepository: BeerRepository
    private lateinit var beerMapper: BeerMapper

    override fun getBeerById(id: UUID): BeerDTO? {
        TODO("Not yet implemented")
    }

    override fun listBeer(): List<BeerDTO> {
        TODO("Not yet implemented")
    }

    override fun save(beerDTO: BeerDTO): BeerDTO {
        TODO("Not yet implemented")
    }

    override fun updateById(id: UUID, newBeerDTO: BeerDTO) {
        TODO("Not yet implemented")
    }

    override fun patchById(id: UUID, newBeerDTO: BeerDTO) {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: UUID) {
        TODO("Not yet implemented")
    }
}