package com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.controllers

import com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.services.BeerService
import com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.services.BeerServiceImpl
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(BeerController::class)
class BeerControllerTestKotlin {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var beerService: BeerService

    private val beerServiceImpl = BeerServiceImpl()

    @Test
    fun getAllBeersList() {
        given(beerService.listBeer()).willReturn(beerServiceImpl.listBeer())

        mockMvc.perform(
            get("/api/v1/beers")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()", equalTo(beerServiceImpl.listBeer().size)))
    }

    @Test
    fun getBeerById() {
        val testBeer = beerServiceImpl.listBeer().first()

        given(beerService.getBeerById(testBeer.id)).willReturn(testBeer)

        mockMvc.perform(
            get("/api/v1/beers/${testBeer.id}")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", equalTo(testBeer.id.toString())))
            .andExpect(jsonPath("$.name", equalTo(testBeer.name)))
    }
}
