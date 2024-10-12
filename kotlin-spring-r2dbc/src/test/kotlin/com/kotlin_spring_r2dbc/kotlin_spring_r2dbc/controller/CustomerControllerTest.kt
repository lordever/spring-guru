package com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.controller

import com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.model.CustomerDTO
import com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.repositories.CustomerRepositoryTest
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@SpringBootTest
@AutoConfigureWebTestClient
class CustomerControllerTest {
    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    @Order(1)
    fun testListCustomers() {
        webTestClient
            .get()
            .uri(CustomerController.CUSTOMER_PATH)
            .exchange()
            .expectStatus().isOk
            .expectHeader().valueEquals("Content-type", "application/json")
            .expectBody().jsonPath("$.size()").isEqualTo(5)
    }

    @Test
    @Order(2)
    fun testGetCustomer() {
        webTestClient
            .get()
            .uri(CustomerController.CUSTOMER_PATH_ID, 1)
            .exchange()
            .expectStatus().isOk
            .expectHeader().valueEquals("Content-type", "application/json")
            .expectBody(CustomerDTO::class.java)
    }

    @Test
    @Order(3)
    fun testUpdateCustomer() {
        webTestClient
            .put()
            .uri(CustomerController.CUSTOMER_PATH_ID, 1)
            .body(Mono.just(CustomerRepositoryTest.getTestCustomer()), CustomerDTO::class.java)
            .exchange()
            .expectStatus().isNoContent
    }

    @Test
    fun testCreateCustomer() {
        webTestClient
            .post()
            .uri(CustomerController.CUSTOMER_PATH)
            .body(Mono.just(CustomerRepositoryTest.getTestCustomer()), CustomerDTO::class.java)
            .exchange()
            .expectStatus().isCreated
    }

    @Test
    @Order(999)
    fun testDeleteCustomer() {
        webTestClient
            .delete()
            .uri(CustomerController.CUSTOMER_PATH_ID, 1)
            .exchange()
            .expectStatus().isNoContent
    }
}