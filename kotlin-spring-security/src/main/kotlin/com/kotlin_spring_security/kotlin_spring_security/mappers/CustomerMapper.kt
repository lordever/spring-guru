package com.kotlin_spring_security.kotlin_spring_security.mappers

import com.kotlin_spring_security.kotlin_spring_security.entities.Customer
import com.kotlin_spring_security.kotlin_spring_security.models.CustomerDTO
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