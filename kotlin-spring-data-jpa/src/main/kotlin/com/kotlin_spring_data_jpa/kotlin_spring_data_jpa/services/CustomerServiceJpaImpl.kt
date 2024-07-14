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

    override fun save(customerDTO: CustomerDTO): CustomerDTO =
        mapper.toDto(
            repository.save(
                mapper.toCustomer(customerDTO)
            )
        )

    override fun updateById(id: UUID, customerDTO: CustomerDTO): CustomerDTO? {
        return repository.findById(id).map { foundCustomer ->
            foundCustomer.apply {
                name = customerDTO.name
            }

            mapper.toDto(foundCustomer)
        }.orElse(null)
    }

    override fun patchById(id: UUID, customerDTO: CustomerDTO): CustomerDTO? {
        return repository.findById(id).map { foundCustomer ->
            if (customerDTO.name != null) {
                foundCustomer.name = customerDTO.name
            }

            mapper.toDto(foundCustomer)
        }.orElse(null)
    }

    override fun deleteById(id: UUID): Boolean {
        if (repository.existsById(id)) {
            repository.deleteById(id)
            return true
        }

        return false
    }
}