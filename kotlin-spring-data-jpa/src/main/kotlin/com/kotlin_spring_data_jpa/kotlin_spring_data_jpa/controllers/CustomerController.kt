package com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.controllers

import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.models.CustomerDTO
import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.services.CustomerService
import mu.KotlinLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
class CustomerController(val customerService: CustomerService) {
    private val logger = KotlinLogging.logger {}

    companion object {
        const val BASE_CUSTOMERS_PATH = "/api/v1/customers"
        const val CUSTOMERS_PATH_WITH_ID = "${BASE_CUSTOMERS_PATH}/{id}";
    }

    @GetMapping(BASE_CUSTOMERS_PATH)
    fun findAll(): List<CustomerDTO> = customerService.findAll()

    @GetMapping(CUSTOMERS_PATH_WITH_ID)
    fun findById(@PathVariable id: UUID): CustomerDTO? {
        logger.debug { "Get customer by id $id" }
        return customerService.findById(id) ?: throw NotFoundException()
    }

    @PostMapping(BASE_CUSTOMERS_PATH)
    fun saveCustomer(@RequestBody customerDTO: CustomerDTO): ResponseEntity<CustomerDTO> {
        val savedCustomer = customerService.save(customerDTO)
        val headers = HttpHeaders()
        headers.add("Location", "/api/v1/customers/${savedCustomer.id}")

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .headers(headers)
            .body(savedCustomer)
    }

    @PutMapping(CUSTOMERS_PATH_WITH_ID)
    fun updateById(@PathVariable id: UUID, @RequestBody customerDTO: CustomerDTO): ResponseEntity<Void> {
        customerService.updateById(id, customerDTO)

        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @PatchMapping(CUSTOMERS_PATH_WITH_ID)
    fun patchById(@PathVariable id: UUID, @RequestBody customerDTO: CustomerDTO): ResponseEntity<Void> {
        customerService.patchById(id, customerDTO)

        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @DeleteMapping(CUSTOMERS_PATH_WITH_ID)
    fun deleteById(@PathVariable id: UUID): ResponseEntity<Void> {
        customerService.deleteById(id)

        return ResponseEntity(HttpStatus.NO_CONTENT)

    }
}