package com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.models.Beer
import com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.services.BeerService
import com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.services.BeerServiceImpl
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.argThat
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.any
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(BeerController::class)
class BeerControllerTestKotlin {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @MockBean
    lateinit var beerService: BeerService

    private lateinit var beerServiceImpl: BeerServiceImpl

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        beerServiceImpl = BeerServiceImpl()
    }

    @Test
    fun testGetAllBeersList() {
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
    fun testGetBeerById() {
        val testBeer = beerServiceImpl.listBeer().first()

        val beerId = requireNotNull(testBeer.id) { "Beer ID cannot be null" }
        given(beerService.getBeerById(beerId)).willReturn(testBeer)

        mockMvc.perform(
            get("/api/v1/beers/${testBeer.id}")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", equalTo(testBeer.id.toString())))
            .andExpect(jsonPath("$.name", equalTo(testBeer.name)))
    }

    @Test
    fun testCreateNewBeer() {
        val testBeer = beerServiceImpl.listBeer().first()

        testBeer.id = null
        testBeer.version = null

        //TODO: can't resolve this issue yet
        given(beerService.save(argThat { it is Beer })).willReturn(beerServiceImpl.listBeer().first())

        mockMvc.perform(
            post("/api/v1/beers")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testBeer))
        )
            .andExpect(status().isCreated)
            .andExpect(header().exists("Location"))
    }
}
