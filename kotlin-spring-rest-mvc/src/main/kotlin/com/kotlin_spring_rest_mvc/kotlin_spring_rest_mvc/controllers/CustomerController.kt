package com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.controllers

import com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.models.Customer
import com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.services.CustomerService
import mu.KotlinLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
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
}