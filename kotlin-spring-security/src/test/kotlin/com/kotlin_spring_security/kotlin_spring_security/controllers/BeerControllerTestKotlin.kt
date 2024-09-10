package com.kotlin_spring_security.kotlin_spring_security.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.kotlin_spring_security.kotlin_spring_security.config.Oauth2SecurityConfig
import com.kotlin_spring_security.kotlin_spring_security.models.BeerDTO
import com.kotlin_spring_security.kotlin_spring_security.services.BeerService
import com.kotlin_spring_security.kotlin_spring_security.services.BeerServiceImpl
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
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.Instant
import java.util.*

@WebMvcTest(BeerController::class)
@Import(Oauth2SecurityConfig::class)
class BeerControllerTestKotlin {

    companion object {
        val jwtProcessor: SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor = jwt().jwt { jwt ->
            jwt.claims { claims ->
                claims["scope"] = "message-read"
                claims["scope"] = "message-write"
            }
                .subject("messaging-client")
                .notBefore(Instant.now().minusSeconds(5L))
        }
    }

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
        every { beerService.listBeer(null, null, false, null, null) } returns beerServiceImpl.listBeer(
            null,
            null,
            false,
            null,
            null
        )

        mockMvc.perform(
            get(BeerController.BASE_BEER_PATH)
                .with(jwtProcessor)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.length()", equalTo(3)))
    }

    @Test
    fun testGetBeerById() {
        val testBeer = beerServiceImpl.listBeer(null, null, false, 1, 25).first()

        val beerId = requireNotNull(testBeer.id) { "Beer ID cannot be null" }
        every { beerService.getBeerById(beerId) } returns testBeer

        mockMvc.perform(
            get(BeerController.BEER_PATH_WITH_ID, beerId)
                .with(jwtProcessor)
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

        mockMvc.perform(
            get(BeerController.BEER_PATH_WITH_ID, UUID.randomUUID())
                .with(jwtProcessor)
        )
            .andExpect(status().isNotFound)
    }

    @Test
    fun testCreateNewBeer() {
        val testBeer = beerServiceImpl.listBeer(null, null, false, 1, 25).first().apply {
            id = null
            version = null
        }

        every { beerService.save(any()) } returns beerServiceImpl.listBeer(null, null, false, 1, 25).first()

        mockMvc.perform(
            post(BeerController.BASE_BEER_PATH)
                .with(jwtProcessor)
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

        every { beerService.save(any()) } returns beerServiceImpl.listBeer(null, null, false, 1, 25).first()

        val mockMvcResult = mockMvc.perform(
            post(BeerController.BASE_BEER_PATH)
                .with(jwtProcessor)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testBeer))
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.length()", equalTo(6)))
            .andReturn()

        println(mockMvcResult)
    }

    @Test
    fun testUpdateNewBeer() {
        val testBeerDTO = beerServiceImpl.listBeer(null, null, false, 1, 25).first()

        every { beerService.updateById(any(), any()) } returns testBeerDTO

        mockMvc.perform(
            put(BeerController.BEER_PATH_WITH_ID, testBeerDTO.id)
                .with(jwtProcessor)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testBeerDTO))
        )
            .andExpect(status().isNoContent)

        verify { beerService.updateById(any(), any()) }
    }

    @Test
    fun testDeleteBeer() {
        val testBeer = beerServiceImpl.listBeer(null, null, false, 1, 25).first()

        val uuidSlot = slot<UUID>()
        every { beerService.deleteById(capture(uuidSlot)) } returns true

        assertNotNull(testBeer.id, "The beer ID should not be null")

        mockMvc.perform(
            delete(BeerController.BEER_PATH_WITH_ID, testBeer.id)
                .with(jwtProcessor)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isNoContent())

        verify { beerService.deleteById(any()) }

        assertThat(uuidSlot.captured).isEqualTo(testBeer.id)
    }

    @Test
    fun testPatchBeer() {
        val testBeer = beerServiceImpl.listBeer(null, null, false, 1, 25).first()
        val beerMap: MutableMap<String, Any> = HashMap()
        beerMap["name"] = "New Name"

        val uuidSlot = slot<UUID>()
        val beerSlot = slot<BeerDTO>()
        every { beerService.patchById(capture(uuidSlot), capture(beerSlot)) } returns testBeer

        mockMvc.perform(
            patch(BeerController.BEER_PATH_WITH_ID, testBeer.id)
                .with(jwtProcessor)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beerMap))
        )
            .andExpect(status().isNoContent)

        verify { beerService.patchById(any(), any()) }
        assertThat(uuidSlot.captured).isEqualTo(testBeer.id)
        assertThat(beerSlot.captured.name).isEqualTo(beerMap["name"])
    }
}
