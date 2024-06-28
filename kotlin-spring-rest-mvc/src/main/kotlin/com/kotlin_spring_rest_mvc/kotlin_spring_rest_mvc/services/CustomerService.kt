package com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.services

import com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.models.Customer
import java.util.*

interface CustomerService {
    fun findAll(): List<Customer>
    fun findById(id: UUID): Customer?
    fun save(customer: Customer): Customer
    fun updateById(id: UUID, customer: Customer): Customer?
}