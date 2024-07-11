package com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.mappers

import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.entities.Beer
import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.models.BeerDTO
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
interface BeerMapper {
    fun toDto(beer: Beer): BeerDTO
    fun toEntity(beerDTO: BeerDTO): Beer

    companion object {
        val INSTANCE: BeerMapper = Mappers.getMapper(BeerMapper::class.java)
    }
}