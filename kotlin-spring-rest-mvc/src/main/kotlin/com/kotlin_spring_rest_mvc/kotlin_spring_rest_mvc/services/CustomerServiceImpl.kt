package com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.services

import com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.models.Customer
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class CustomerServiceImpl : CustomerService {
    private val customerMap: MutableMap<UUID, Customer> = mutableMapOf()
    private val log = KotlinLogging.logger {}

    init {
        val customer1 = Customer(
            id = UUID.randomUUID(),
            name = "John Doe",
            version = 1,
            createdDate = LocalDateTime.now(),
            lastModifiedDate = LocalDateTime.now()
        )

        val customer2 = Customer(
            id = UUID.randomUUID(),
            name = "Jane Smith",
            version = 1,
            createdDate = LocalDateTime.now(),
            lastModifiedDate = LocalDateTime.now()
        )

        val customer3 = Customer(
            id = UUID.randomUUID(),
            name = "Alice Johnson",
            version = 1,
            createdDate = LocalDateTime.now(),
            lastModifiedDate = LocalDateTime.now()
        )

        val customer4 = Customer(
            id = UUID.randomUUID(),
            name = "Bob Brown",
            version = 1,
            createdDate = LocalDateTime.now(),
            lastModifiedDate = LocalDateTime.now()
        )

        customer1.id?.let { customerMap[it] = customer1 }
        customer2.id?.let { customerMap[it] = customer2 }
        customer3.id?.let { customerMap[it] = customer3 }
        customer4.id?.let { customerMap[it] = customer4 }
    }

    override fun findAll(): List<Customer> {
        log.debug { "Get Beer Id in service was called" }

        return customerMap.values.toList()
    }

    override fun findById(id: UUID): Customer? {
        return customerMap[id]
    }

    override fun save(customer: Customer): Customer {
        val newCustomer = Customer(
            id = UUID.randomUUID(),
            name = customer.name,
            version = customer.version,
            createdDate = LocalDateTime.now(),
            lastModifiedDate = LocalDateTime.now()
        )

        newCustomer.id?.let { customerMap[newCustomer.id] }

        return newCustomer
    }

    override fun updateById(id: UUID, customer: Customer) {
        val existingCustomer = customerMap[id]

        if (existingCustomer != null) {
            existingCustomer.name = customer.name
            existingCustomer.version = customer.version
            existingCustomer.lastModifiedDate = LocalDateTime.now()
        } else {
            log.debug { "Customer $id wasn't found" }
        }
    }

    override fun patchById(id: UUID, customer: Customer) {
        val existingCustomer = customerMap[id]

        if (existingCustomer != null) {
            if (customer.name != null) {
                existingCustomer.name = customer.name
            }
            if (customer.version != null) {
                existingCustomer.version = customer.version
            }

            existingCustomer.lastModifiedDate = LocalDateTime.now()
        } else {
            log.debug { "Customer $id wasn't found" }
        }
    }

    override fun deleteById(id: UUID) {
        customerMap.remove(id)
    }
}