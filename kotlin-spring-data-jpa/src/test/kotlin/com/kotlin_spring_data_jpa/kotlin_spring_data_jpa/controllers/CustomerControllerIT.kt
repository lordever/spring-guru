package com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.controllers

import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.entities.Customer
import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.models.CustomerDTO
import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.repositories.CustomerRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import java.util.*

@SpringBootTest
class CustomerControllerIT {
    @Autowired
    private lateinit var customerController: CustomerController

    @Autowired
    private lateinit var customerRepository: CustomerRepository


    @Test
    fun testListCustomers() {
        val listCustomers: List<CustomerDTO> = customerController.findAll()

        assertThat(listCustomers.size).isEqualTo(4)
    }

    @Rollback
    @Transactional
    @Test
    fun testListCustomersNotFound() {
        customerRepository.deleteAll()
        val listCustomers: List<CustomerDTO> = customerController.findAll()

        assertThat(listCustomers.size).isEqualTo(0)
    }

    @Test
    fun testGetCustomer() {
        val testCustomer = customerRepository.findAll()[0]
        val customerId = requireNotNull(testCustomer.id) { "Customer ID cannot be null" }
        val customer = customerController.findById(customerId)

        assertThat(customer).isNotNull
    }

    @Test
    fun testGetCustomerNotFound() {
        assertThrows(NotFoundException::class.java) {
            customerController.findById(UUID.randomUUID())
        }
    }
}