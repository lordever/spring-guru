package com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.controller

import com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.model.CustomerDTO
import com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.services.CustomerService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
class CustomerController(
    var customerService: CustomerService
) {
    companion object {
        const val CUSTOMER_PATH = "/api/v2/customers"
        const val CUSTOMER_PATH_ID = "$CUSTOMER_PATH/{customerId}"
    }

    @GetMapping(CUSTOMER_PATH)
    fun listCustomers(): Flux<CustomerDTO> = customerService.listCustomers()

    @GetMapping(CUSTOMER_PATH_ID)
    fun getCustomerById(@PathVariable customerId: Int): Mono<CustomerDTO> = customerService.getCustomerById(customerId)

    @PostMapping(CUSTOMER_PATH)
    fun createCustomer(@RequestBody customerDTO: CustomerDTO): Mono<ResponseEntity<Void>> =
        customerService.createCustomer(customerDTO).map { customer ->
            ResponseEntity.created(
                UriComponentsBuilder.fromHttpUrl("http://localhost:8080" + CUSTOMER_PATH + customer.id).build()
                    .toUri()
            ).build()
        }

    @PutMapping(CUSTOMER_PATH_ID)
    fun updateCustomer(
        @PathVariable customerId: Int,
        @Validated @RequestBody customerDTO: CustomerDTO
    ): Mono<ResponseEntity<Void>> =
        customerService.updateCustomer(customerId, customerDTO)
            .map { ResponseEntity.ok().build() }

    @PatchMapping(CUSTOMER_PATH_ID)
    fun patchCustomer(
        @PathVariable customerId: Int,
        @Validated @RequestBody customerDTO: CustomerDTO
    ): Mono<ResponseEntity<Void>> =
        customerService.patchCustomer(customerId, customerDTO)
            .map { ResponseEntity.ok().build() }

    @DeleteMapping(CUSTOMER_PATH_ID)
    fun deleteCustomer(
        @PathVariable customerId: Int
    ): Mono<ResponseEntity<Void>> =
        customerService.deleteCustomerById(customerId)
            .then(Mono.fromCallable { ResponseEntity.noContent().build() })

}