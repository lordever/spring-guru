package com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.mappers

import com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.domain.Customer
import com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.model.CustomerDTO
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface CustomerMapper {
    fun toDto(customer: Customer): CustomerDTO
    fun toCustomer(customerDTO: CustomerDTO): Customer
}