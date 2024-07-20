package com.kotlin_spring_mysql.kotlin_spring_mysql.mappers

import com.kotlin_spring_mysql.kotlin_spring_mysql.entities.Beer
import com.kotlin_spring_mysql.kotlin_spring_mysql.models.BeerDTO
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