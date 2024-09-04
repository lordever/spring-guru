package com.kotlin_spring_security.kotlin_spring_security.services

import com.kotlin_spring_security.kotlin_spring_security.models.CustomerDTO
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class CustomerServiceImpl : CustomerService {
    private val customerDTOMap: MutableMap<UUID, CustomerDTO> = mutableMapOf()
    private val log = KotlinLogging.logger {}

    init {
        val customerDTO1 = CustomerDTO(
            id = UUID.randomUUID(),
            name = "John Doe",
            version = 1,
            createdDate = LocalDateTime.now(),
            lastModifiedDate = LocalDateTime.now()
        )

        val customerDTO2 = CustomerDTO(
            id = UUID.randomUUID(),
            name = "Jane Smith",
            version = 1,
            createdDate = LocalDateTime.now(),
            lastModifiedDate = LocalDateTime.now()
        )

        val customerDTO3 = CustomerDTO(
            id = UUID.randomUUID(),
            name = "Alice Johnson",
            version = 1,
            createdDate = LocalDateTime.now(),
            lastModifiedDate = LocalDateTime.now()
        )

        val customerDTO4 = CustomerDTO(
            id = UUID.randomUUID(),
            name = "Bob Brown",
            version = 1,
            createdDate = LocalDateTime.now(),
            lastModifiedDate = LocalDateTime.now()
        )

        customerDTO1.id?.let { customerDTOMap[it] = customerDTO1 }
        customerDTO2.id?.let { customerDTOMap[it] = customerDTO2 }
        customerDTO3.id?.let { customerDTOMap[it] = customerDTO3 }
        customerDTO4.id?.let { customerDTOMap[it] = customerDTO4 }
    }

    override fun findAll(): List<CustomerDTO> {
        log.debug { "Get Beer Id in service was called" }

        return customerDTOMap.values.toList()
    }

    override fun findById(id: UUID): CustomerDTO? {
        return customerDTOMap[id]
    }

    override fun save(customerDTO: CustomerDTO): CustomerDTO {
        val newCustomerDTO = CustomerDTO(
            id = UUID.randomUUID(),
            name = customerDTO.name,
            version = customerDTO.version,
            createdDate = LocalDateTime.now(),
            lastModifiedDate = LocalDateTime.now()
        )

        newCustomerDTO.id?.let { customerDTOMap[newCustomerDTO.id] }

        return newCustomerDTO
    }

    override fun updateById(id: UUID, customerDTO: CustomerDTO): CustomerDTO? {
        val existingCustomer = customerDTOMap[id]

        if (existingCustomer != null) {
            existingCustomer.name = customerDTO.name
            existingCustomer.version = customerDTO.version
            existingCustomer.lastModifiedDate = LocalDateTime.now()

            return existingCustomer
        } else {
            log.debug { "Customer $id wasn't found" }
            return null
        }
    }

    override fun patchById(id: UUID, customerDTO: CustomerDTO): CustomerDTO? {
        val existingCustomer = customerDTOMap[id]

        if (existingCustomer != null) {
            if (customerDTO.name != null) {
                existingCustomer.name = customerDTO.name
            }
            if (customerDTO.version != null) {
                existingCustomer.version = customerDTO.version
            }

            existingCustomer.lastModifiedDate = LocalDateTime.now()

            return existingCustomer
        } else {
            log.debug { "Customer $id wasn't found" }
            return null
        }
    }

    override fun deleteById(id: UUID): Boolean {
        customerDTOMap.remove(id)

        return true
    }
}