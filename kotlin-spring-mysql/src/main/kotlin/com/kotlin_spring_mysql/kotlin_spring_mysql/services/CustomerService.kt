package com.kotlin_spring_mysql.kotlin_spring_mysql.services

import com.kotlin_spring_mysql.kotlin_spring_mysql.models.CustomerDTO
import java.util.*

interface CustomerService {
    fun findAll(): List<CustomerDTO>
    fun findById(id: UUID): CustomerDTO?
    fun save(customerDTO: CustomerDTO): CustomerDTO
    fun updateById(id: UUID, customerDTO: CustomerDTO): CustomerDTO?
    fun patchById(id: UUID, customerDTO: CustomerDTO): CustomerDTO?
    fun deleteById(id: UUID): Boolean
}