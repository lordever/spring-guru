package com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.mappers

import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.entities.Customer
import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.models.CustomerDTO
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper(componentModel = "spring")
interface CustomerMapper {

    fun dtoToCustomer(dto: CustomerDTO): Customer
    fun customerToDto(customer: Customer): CustomerDTO

    companion object {
        val INSTANCE: CustomerMapper = Mappers.getMapper(CustomerMapper::class.java)
    }

}