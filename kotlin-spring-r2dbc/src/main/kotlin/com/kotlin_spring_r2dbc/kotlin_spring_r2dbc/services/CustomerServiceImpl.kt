package com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.services

import com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.mappers.CustomerMapper
import com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.model.CustomerDTO
import com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.repositories.CustomerRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class CustomerServiceImpl(
    var customerRepository: CustomerRepository,
    var customerMapper: CustomerMapper
) : CustomerService {
    override fun listCustomers(): Flux<CustomerDTO> =
        customerRepository.findAll().map(customerMapper::toDto)

    override fun getCustomerById(customerId: Int): Mono<CustomerDTO> =
        customerRepository.findById(customerId).map(customerMapper::toDto)

    override fun createCustomer(customerDTO: CustomerDTO): Mono<CustomerDTO> =
        customerRepository
            .save(customerMapper.toCustomer(customerDTO))
            .map(customerMapper::toDto)

    override fun updateCustomer(customerId: Int, dto: CustomerDTO): Mono<CustomerDTO> =
        customerRepository.findById(customerId).map { foundCustomer ->
            foundCustomer.name = dto.name

            foundCustomer
        }
            .flatMap(customerRepository::save)
            .map(customerMapper::toDto)

    override fun patchCustomer(customerId: Int, dto: CustomerDTO): Mono<CustomerDTO> =
        customerRepository.findById(customerId).map { foundCustomer ->
            if (dto.name != null) foundCustomer.name = dto.name

            foundCustomer
        }
            .flatMap(customerRepository::save)
            .map(customerMapper::toDto)

    override fun deleteCustomerById(customerId: Int): Mono<Void> =
        customerRepository.deleteById(customerId)
}