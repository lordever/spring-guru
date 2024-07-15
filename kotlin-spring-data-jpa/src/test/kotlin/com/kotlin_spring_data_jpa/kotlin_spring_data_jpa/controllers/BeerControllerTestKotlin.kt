package com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.models.BeerDTO
import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.services.BeerService
import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.services.BeerServiceImpl
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.util.*

@WebMvcTest(BeerController::class)
class BeerControllerTestKotlin {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @MockkBean
    lateinit var beerService: BeerService

    private lateinit var beerServiceImpl: BeerServiceImpl

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        beerServiceImpl = BeerServiceImpl()
    }

    @Test
    fun testGetAllBeersList() {
        every { beerService.listBeer() } returns beerServiceImpl.listBeer()

        mockMvc.perform(
            get(BeerController.BASE_BEER_PATH)
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
        every { beerService.getBeerById(beerId) } returns testBeer

        mockMvc.perform(
            get(BeerController.BEER_PATH_WITH_ID, beerId)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", equalTo(testBeer.id.toString())))
            .andExpect(jsonPath("$.name", equalTo(testBeer.name)))
    }

    @Test
    fun testGetBeerByIdNotFound() {
        every { beerService.getBeerById(any()) } returns null

        mockMvc.perform(get(BeerController.BEER_PATH_WITH_ID, UUID.randomUUID()))
            .andExpect(status().isNotFound)
    }

    @Test
    fun testCreateNewBeer() {
        val testBeer = beerServiceImpl.listBeer().first().apply {
            id = null
            version = null
        }

        every { beerService.save(any()) } returns beerServiceImpl.listBeer().first()

        mockMvc.perform(
            post(BeerController.BASE_BEER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testBeer))
        )
            .andExpect(status().isCreated)
            .andExpect(header().exists("Location"))
    }

    @Test
    fun testCreateNewBeerNullBeerName() {
        val testBeer = BeerDTO()

        every { beerService.save(any()) } returns beerServiceImpl.listBeer().first()

        mockMvc.perform(
            post(BeerController.BASE_BEER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testBeer))
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun testUpdateNewBeer() {
        val testBeerDTO = beerServiceImpl.listBeer().first()

        every { beerService.updateById(any(), any()) } returns testBeerDTO

        mockMvc.perform(
            put(BeerController.BEER_PATH_WITH_ID, testBeerDTO.id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testBeerDTO))
        )
            .andExpect(status().isNoContent)

        verify { beerService.updateById(any(), any()) }
    }

    @Test
    fun testDeleteBeer() {
        val testBeer = beerServiceImpl.listBeer().first()

        val uuidSlot = slot<UUID>()
        every { beerService.deleteById(capture(uuidSlot)) } returns true

        assertNotNull(testBeer.id, "The beer ID should not be null")

        mockMvc.perform(
            delete(BeerController.BEER_PATH_WITH_ID, testBeer.id)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isNoContent())

        verify { beerService.deleteById(any()) }

        assertThat(uuidSlot.captured).isEqualTo(testBeer.id)
    }

    @Test
    fun testPatchBeer() {
        val testBeer = beerServiceImpl.listBeer().first()
        val beerMap: MutableMap<String, Any> = HashMap()
        beerMap["name"] = "New Name"

        val uuidSlot = slot<UUID>()
        val beerSlot = slot<BeerDTO>()
        every { beerService.patchById(capture(uuidSlot), capture(beerSlot)) } returns testBeer

        mockMvc.perform(
            patch(BeerController.BEER_PATH_WITH_ID, testBeer.id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beerMap))
        )
            .andExpect(status().isNoContent())

        verify { beerService.patchById(any(), any()) }
        assertThat(uuidSlot.captured).isEqualTo(testBeer.id)
        assertThat(beerSlot.captured.name).isEqualTo(beerMap["name"])
    }
}
