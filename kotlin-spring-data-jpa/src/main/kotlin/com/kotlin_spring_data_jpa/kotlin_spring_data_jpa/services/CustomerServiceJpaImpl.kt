package com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.services

import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.mappers.CustomerMapper
import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.models.CustomerDTO
import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.repositories.CustomerRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import java.util.*

@Primary
@Service
class CustomerServiceJpaImpl : CustomerService {

    private lateinit var customerRepository: CustomerRepository
    private lateinit var customerMapper: CustomerMapper


    override fun findAll(): List<CustomerDTO> {
        TODO("Not yet implemented")
    }

    override fun findById(id: UUID): CustomerDTO? {
        TODO("Not yet implemented")
    }

    override fun save(customerDTO: CustomerDTO): CustomerDTO {
        TODO("Not yet implemented")
    }

    override fun updateById(id: UUID, customerDTO: CustomerDTO) {
        TODO("Not yet implemented")
    }

    override fun patchById(id: UUID, customerDTO: CustomerDTO) {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: UUID) {
        TODO("Not yet implemented")
    }
}