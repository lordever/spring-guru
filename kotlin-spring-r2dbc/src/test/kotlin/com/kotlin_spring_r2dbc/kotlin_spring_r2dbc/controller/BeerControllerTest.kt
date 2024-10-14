package com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.controller

import com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.model.BeerDTO
import com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.repositories.BeerRepositoryTest
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
class BeerControllerTest {
    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    @Order(1)
    fun testListBeers() {
        webTestClient
            .get()
            .uri(BeerController.BEER_PATH)
            .exchange()
            .expectStatus().isOk
            .expectHeader().valueEquals("Content-type", "application/json")
            .expectBody().jsonPath("$.size()").isEqualTo(3)
    }

    @Test
    @Order(2)
    fun testGetBeerById() {
        webTestClient
            .get()
            .uri(BeerController.BEER_PATH_ID, 1)
            .exchange()
            .expectStatus().isOk
            .expectHeader().valueEquals("Content-type", "application/json")
            .expectBody(BeerDTO::class.java)
    }

    @Test
    fun testGetBeerByIdNotFound() {
        webTestClient
            .get()
            .uri(BeerController.BEER_PATH_ID, 1111111)
            .exchange()
            .expectStatus().isNotFound
    }

    @Test
    @Order(3)
    fun testUpdateBeer() {
        webTestClient.put()
            .uri(BeerController.BEER_PATH_ID, 1)
            .body(Mono.just(BeerRepositoryTest.getTestBeer()), BeerDTO::class.java)
            .exchange()
            .expectStatus().isNoContent
    }

    @Test
    @Order(4)
    fun testUpdateBeerBad() {
        val testBeer = BeerRepositoryTest.getTestBeer()
        testBeer.style = ""

        webTestClient.put()
            .uri(BeerController.BEER_PATH_ID, 1)
            .body(Mono.just(testBeer), BeerDTO::class.java)
            .exchange()
            .expectStatus().isBadRequest
    }

    @Test
    fun testUpdateBeerNotFound() {
        val testBeer = BeerRepositoryTest.getTestBeer()

        webTestClient.put()
            .uri(BeerController.BEER_PATH_ID, 111)
            .body(Mono.just(testBeer), BeerDTO::class.java)
            .exchange()
            .expectStatus().isNotFound
    }

    @Test
    fun testPatchBeerNotFound() {
        val testBeer = BeerRepositoryTest.getTestBeer()

        webTestClient.patch()
            .uri(BeerController.BEER_PATH_ID, 111)
            .body(Mono.just(testBeer), BeerDTO::class.java)
            .exchange()
            .expectStatus().isNotFound
    }

    @Test
    fun testCreateBeer() {
        webTestClient
            .post()
            .uri(BeerController.BEER_PATH)
            .body(Mono.just(BeerRepositoryTest.getTestBeer()), BeerDTO::class.java)
            .header("Content-type", "application/json")
            .exchange()
            .expectStatus().isCreated
            .expectHeader().location("http://localhost:8080/api/v2/beers/4")
    }

    @Test
    fun testCreateBeerBad() {
        val testBeer = BeerRepositoryTest.getTestBeer()
        testBeer.name = ""


        webTestClient
            .post()
            .uri(BeerController.BEER_PATH)
            .body(Mono.just(testBeer), BeerDTO::class.java)
            .header("Content-type", "application/json")
            .exchange()
            .expectStatus().isBadRequest
    }

    @Test
    @Order(999)
    fun testDeleteBeer() {
        webTestClient.delete()
            .uri(BeerController.BEER_PATH_ID, 1)
            .exchange()
            .expectStatus().isNoContent
    }
}