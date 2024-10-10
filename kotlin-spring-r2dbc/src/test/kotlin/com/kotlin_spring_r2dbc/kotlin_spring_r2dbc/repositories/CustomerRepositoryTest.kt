package com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.repositories

import com.fasterxml.jackson.databind.ObjectMapper
import com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.config.DatabaseConfig
import com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.domain.Customer
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest
import org.springframework.context.annotation.Import

@DataR2dbcTest
@Import(DatabaseConfig::class)
class CustomerRepositoryTest {
    @Autowired
    lateinit var customerRepository: CustomerRepository

    @Test
    fun testCreateJson() {
        val objectMapper = ObjectMapper()

        println(
            objectMapper.writeValueAsString(
                getTestCustomer()
            )
        )
    }

    @Test
    fun saveNewBeer() {
        customerRepository.save(getTestCustomer())
            .subscribe { customer -> println(customer.toString()) }
    }

    fun getTestCustomer(): Customer =
        Customer(
            name = "Alex Johnson"
        )
}