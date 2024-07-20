package com.kotlin_spring_mysql.kotlin_spring_mysql.mappers

import com.kotlin_spring_mysql.kotlin_spring_mysql.entities.Customer
import com.kotlin_spring_mysql.kotlin_spring_mysql.models.CustomerDTO
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper(componentModel = "spring")
interface CustomerMapper {

    fun toCustomer(dto: CustomerDTO): Customer
    fun toDto(customer: Customer): CustomerDTO

    companion object {
        val INSTANCE: CustomerMapper = Mappers.getMapper(CustomerMapper::class.java)
    }

}