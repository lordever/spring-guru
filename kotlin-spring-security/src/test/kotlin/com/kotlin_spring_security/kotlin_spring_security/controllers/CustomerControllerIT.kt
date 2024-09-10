package com.kotlin_spring_security.kotlin_spring_security.controllers

import com.kotlin_spring_security.kotlin_spring_security.entities.Customer
import com.kotlin_spring_security.kotlin_spring_security.mappers.CustomerMapper
import com.kotlin_spring_security.kotlin_spring_security.models.CustomerDTO
import com.kotlin_spring_security.kotlin_spring_security.repositories.CustomerRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@SpringBootTest
class CustomerControllerIT {
    companion object {
        val jwtProcessor: SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor = jwt().jwt { jwt ->
            jwt.claims { claims ->
                claims["scope"] = "message-read"
                claims["scope"] = "message-write"
            }
                .subject("messaging-client")
                .notBefore(Instant.now().minusSeconds(5L))
        }
    }

    @Autowired
    private lateinit var customerController: CustomerController

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @Autowired
    private lateinit var customerMapper: CustomerMapper


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

    @Rollback
    @Transactional
    @Test
    fun testSaveCustomer() {
        val customerDto = CustomerDTO(
            name = "Test Customer Name"
        )

        val responseEntity: ResponseEntity<CustomerDTO> = customerController.saveCustomer(customerDto)
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.CREATED)
        assertThat(responseEntity.headers.get("Location")).isNotNull

        val locationUUID: List<String>? = responseEntity.headers.location?.path?.split("/")
        val uuid: UUID = UUID.fromString(locationUUID!![4])

        val customer: Customer = customerRepository.findById(uuid).get()
        assertThat(customer).isNotNull
        assertThat(customer.name).isEqualTo(customerDto.name)
    }

    @Rollback
    @Transactional
    @Test
    fun testUpdateCustomer() {
        val customer: Customer = customerRepository.findAll()[0]
        val customerDTO: CustomerDTO = customerMapper.toDto(customer)
        val newVersion = 2
        customer.id = null
        customer.version = newVersion

        val customerName = "UPDATED"
        customerDTO.name = customerName

        val customerId = requireNotNull(customerDTO.id) { "Customer ID cannot be null" }
        val responseEntity: ResponseEntity<Void> = customerController.updateById(customerId, customerDTO)
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.NO_CONTENT)

        val updatedBeer = customerRepository.findById(customerId).get()
        assertThat(updatedBeer.name).isEqualTo(customerName)
        assertThat(updatedBeer.version).isEqualTo(newVersion)
    }

    @Test
    fun testUpdateCustomerNotFound() {
        assertThrows(NotFoundException::class.java) {
            customerController.updateById(UUID.randomUUID(), CustomerDTO())
        }
    }

    @Rollback
    @Transactional
    @Test
    fun testDeleteBeerById() {
        val customer: Customer = customerRepository.findAll()[0]
        val customerId = requireNotNull(customer.id) { "Customer ID cannot be null" }

        val responseEntity = customerController.deleteById(customerId)
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.NO_CONTENT)
        assertThat(customerRepository.findById(customerId)).isEmpty
    }

    @Rollback
    @Transactional
    @Test
    fun testDeleteBeerByIdNotFound() {
        assertThrows(NotFoundException::class.java) {
            customerController.deleteById(UUID.randomUUID())
        }
    }

    @Rollback
    @Transactional
    @Test
    fun testPatchCustomer() {
        val customer: Customer = customerRepository.findAll()[0]
        val customerDTO: CustomerDTO = customerMapper.toDto(customer)
        val defaultVersion = customer.version
        customerDTO.id = null
        customerDTO.version = null

        val customerName = "UPDATED"
        customerDTO.name = customerName

        val customerId = requireNotNull(customer.id) { "Customer ID cannot be null" }
        val responseEntity: ResponseEntity<Void> = customerController.patchById(customerId, customerDTO)
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.NO_CONTENT)

        val updatedBeer = customerRepository.findById(customerId).get()
        assertThat(updatedBeer.name).isEqualTo(customerName)
        assertThat(updatedBeer.version).isEqualTo(defaultVersion)
    }

    @Test
    fun testPatchCustomerNotFound() {
        assertThrows(NotFoundException::class.java) {
            customerController.patchById(UUID.randomUUID(), CustomerDTO())
        }
    }
}