package com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.services

import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.models.Customer
import java.util.*

interface CustomerService {
    fun findAll(): List<Customer>
    fun findById(id: UUID): Customer?
    fun save(customer: Customer): Customer
    fun updateById(id: UUID, customer: Customer): Customer?
    fun patchById(id: UUID, customer: Customer): Customer?
    fun deleteById(id: UUID)
}