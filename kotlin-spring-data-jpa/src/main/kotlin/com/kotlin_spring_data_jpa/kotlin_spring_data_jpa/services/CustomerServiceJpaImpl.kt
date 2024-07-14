package com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.services

import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.mappers.CustomerMapper
import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.models.CustomerDTO
import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.repositories.CustomerRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import java.util.*

@Primary
@Service
class CustomerServiceJpaImpl(
    private val repository: CustomerRepository,
    private val mapper: CustomerMapper
) : CustomerService {
    override fun findAll(): List<CustomerDTO> = repository.findAll().map(mapper::toDto)

    override fun findById(id: UUID): CustomerDTO? =
        repository.findById(id).map(mapper::toDto).orElse(null)

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