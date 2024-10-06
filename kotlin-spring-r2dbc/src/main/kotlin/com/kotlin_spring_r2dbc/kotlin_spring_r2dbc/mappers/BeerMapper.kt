package com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.mappers

import com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.domain.Beer
import com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.model.BeerDTO
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface BeerMapper {
    fun toDto(beer: Beer): BeerDTO
    fun toBeer(beerDTO: BeerDTO): Beer
}