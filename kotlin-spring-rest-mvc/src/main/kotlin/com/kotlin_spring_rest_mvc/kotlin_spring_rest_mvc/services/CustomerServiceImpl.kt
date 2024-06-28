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
            version = "1",
            createdDate = LocalDateTime.now(),
            lastModifiedDate = LocalDateTime.now()
        )

        val customer2 = Customer(
            id = UUID.randomUUID(),
            name = "Jane Smith",
            version = "1",
            createdDate = LocalDateTime.now(),
            lastModifiedDate = LocalDateTime.now()
        )

        val customer3 = Customer(
            id = UUID.randomUUID(),
            name = "Alice Johnson",
            version = "1",
            createdDate = LocalDateTime.now(),
            lastModifiedDate = LocalDateTime.now()
        )

        val customer4 = Customer(
            id = UUID.randomUUID(),
            name = "Bob Brown",
            version = "1",
            createdDate = LocalDateTime.now(),
            lastModifiedDate = LocalDateTime.now()
        )

        customerMap[customer1.id] = customer1
        customerMap[customer2.id] = customer2
        customerMap[customer3.id] = customer3
        customerMap[customer4.id] = customer4
    }

    override fun findAll(): List<Customer> {
        log.debug { "Get Beer Id in service was called" }

        return customerMap.values.toList()
    }

    override fun findById(id: UUID): Customer? {
        return customerMap[id]
    }
}