package com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.controllers

import com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.models.Customer
import com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.services.CustomerService
import mu.KotlinLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/v1/customers")
class CustomerController(val customerService: CustomerService) {
    private val logger = KotlinLogging.logger {}

    @GetMapping
    fun findAll(): List<Customer> = customerService.findAll()

    @GetMapping("/{id}")
    fun findById(@PathVariable id: UUID): Customer? {
        logger.debug { "Get customer by id $id" }
        return customerService.findById(id)
    }

    @PostMapping
    fun saveCustomer(@RequestBody customer: Customer): ResponseEntity<Customer> {
        val savedCustomer = customerService.save(customer)
        val headers = HttpHeaders()
        headers.add("Location", "/api/v1/customers/${savedCustomer.id}")

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .headers(headers)
            .body(savedCustomer)
    }

    @PutMapping("{id}")
    fun updateById(@PathVariable id: UUID, @RequestBody customer: Customer): ResponseEntity<Void> {
        customerService.updateById(id, customer)

        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @PatchMapping("{id}")
    fun patchById(@PathVariable id: UUID, @RequestBody customer: Customer): ResponseEntity<Customer> {
        val updatedCustomer = customerService.patchById(id, customer)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(updatedCustomer)
    }

    @DeleteMapping("{id}")
    fun deleteById(@PathVariable id: UUID): ResponseEntity<Void> {
        customerService.deleteById(id)

        return ResponseEntity(HttpStatus.NO_CONTENT)

    }
}