package com.kotlin_spring_security.kotlin_spring_security.mappers

import com.kotlin_spring_security.kotlin_spring_security.entities.Beer
import com.kotlin_spring_security.kotlin_spring_security.models.BeerDTO
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper(componentModel = "spring")
interface BeerMapper {
    fun toDto(beer: Beer): BeerDTO
    fun toBeer(beerDTO: BeerDTO): Beer

    companion object {
        val INSTANCE: BeerMapper = Mappers.getMapper(BeerMapper::class.java)
    }
}