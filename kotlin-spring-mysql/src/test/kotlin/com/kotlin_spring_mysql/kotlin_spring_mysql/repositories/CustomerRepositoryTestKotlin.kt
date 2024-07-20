package com.kotlin_spring_mysql.kotlin_spring_mysql.repositories

import com.kotlin_spring_mysql.kotlin_spring_mysql.entities.Customer
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

import org.assertj.core.api.Assertions.assertThat

@DataJpaTest
class CustomerRepositoryTestKotlin {

    @Autowired
    lateinit var customerRepository: CustomerRepository

    @Test
    fun testSaveCustomer() {
        val customer = Customer(
            name = "Test Customer Name"
        )

        val savedCustomer = customerRepository.save(customer)

        assertThat(savedCustomer).isNotNull
        assertThat(savedCustomer.id).isNotNull
        assertThat(savedCustomer.name).isEqualTo(customer.name)
    }

}