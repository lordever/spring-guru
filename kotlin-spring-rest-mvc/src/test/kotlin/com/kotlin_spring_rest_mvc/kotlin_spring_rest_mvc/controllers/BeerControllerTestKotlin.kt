package com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.controllers

import com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.services.BeerService
import com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.services.BeerServiceImpl
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.util.*

@WebMvcTest(BeerController::class)
class BeerControllerTestKotlin {
    @Autowired
    private lateinit var mockMvc: MockMvc

    private val beerService: BeerService = mockk()
    private val beerServiceImpl = BeerServiceImpl()

    @Test
    fun getBeerById() {
        val testBeer = beerServiceImpl.listBeer().first()
        val beerId = UUID.randomUUID()

        every { beerService.getBeerById(any()) } returns testBeer

        mockMvc.get("/api/v1/beers/$beerId") {
            accept(MediaType.APPLICATION_JSON)
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
        }
    }

}