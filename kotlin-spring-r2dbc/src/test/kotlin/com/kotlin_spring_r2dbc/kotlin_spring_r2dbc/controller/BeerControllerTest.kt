package com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.controller

import com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.model.BeerDTO
import com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.repositories.BeerRepositoryTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@SpringBootTest
@AutoConfigureWebTestClient
class BeerControllerTest {
    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
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
    fun testUpdateBeer() {
        webTestClient.put()
            .uri(BeerController.BEER_PATH_ID, 1)
            .body(Mono.just(BeerRepositoryTest.getTestBeer()), BeerDTO::class.java)
            .exchange()
            .expectStatus().isNoContent
    }
}