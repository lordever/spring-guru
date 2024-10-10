package com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.services

import com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.model.CustomerDTO
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface CustomerService {
    fun listCustomers(): Flux<CustomerDTO>
    fun getCustomerById(customerId: Int): Mono<CustomerDTO>
    fun createCustomer(customerDTO: CustomerDTO): Mono<CustomerDTO>
    fun updateCustomer(customerId: Int, dto: CustomerDTO): Mono<CustomerDTO>
    fun patchCustomer(customerId: Int, dto: CustomerDTO): Mono<CustomerDTO>
    fun deleteCustomerById(customerId: Int): Mono<Void>
}