package com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.services

import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.models.CustomerDTO
import java.util.*

interface CustomerService {
    fun findAll(): List<CustomerDTO>
    fun findById(id: UUID): CustomerDTO?
    fun save(customerDTO: CustomerDTO): CustomerDTO
    fun updateById(id: UUID, customerDTO: CustomerDTO)
    fun patchById(id: UUID, customerDTO: CustomerDTO)
    fun deleteById(id: UUID)
}