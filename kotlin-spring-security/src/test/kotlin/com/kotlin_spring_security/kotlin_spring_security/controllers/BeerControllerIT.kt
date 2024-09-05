package com.kotlin_spring_security.kotlin_spring_security.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.kotlin_spring_security.kotlin_spring_security.config.SpringSecurityConfig
import com.kotlin_spring_security.kotlin_spring_security.controllers.BeerControllerTestKotlin.Companion
import com.kotlin_spring_security.kotlin_spring_security.entities.Beer
import com.kotlin_spring_security.kotlin_spring_security.mappers.BeerMapper
import com.kotlin_spring_security.kotlin_spring_security.models.BeerDTO
import com.kotlin_spring_security.kotlin_spring_security.models.BeerStyle
import com.kotlin_spring_security.kotlin_spring_security.repositories.BeerRepository
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.core.IsNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.context.WebApplicationContext
import java.util.*

@SpringBootTest
class BeerControllerIT {
    companion object {
        const val USERNAME = "user1"
        const val PASSWORD = "user123"
    }

    @Autowired
    lateinit var beerRepository: BeerRepository

    @Autowired
    lateinit var beerController: BeerController

    @Autowired
    lateinit var beerMapper: BeerMapper

    @Autowired
    lateinit var wac: WebApplicationContext

    @Autowired
    lateinit var objectMapper: ObjectMapper

    lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp() {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(wac)
            .apply<DefaultMockMvcBuilder>(springSecurity())
            .build()
    }

    @Test
    fun testListBeers() {
        val dtos: Page<BeerDTO> = beerController.listBeers(null, null, false, 1, 2413)

        assertThat(dtos.content.size).isEqualTo(1000)
    }

    @Rollback
    @Transactional
    @Test
    fun testEmptyList() {
        beerRepository.deleteAll()

        val dtos: Page<BeerDTO> = beerController.listBeers(null, null, false, 1, 25)

        assertThat(dtos.content.size).isEqualTo(0)
    }

    @Test
    fun testGetBeerById() {
        val beer = beerRepository.findAll()[0]
        val beerId = requireNotNull(beer.id) { "Beer ID cannot be null" }
        val beerDTO = beerController.getBeerById(beerId)

        assertThat(beerDTO).isNotNull
    }

    @Test
    fun testGetBeerByIdNotFound() {
        assertThrows(NotFoundException::class.java) {
            beerController.getBeerById(UUID.randomUUID())
        }
    }

    @Rollback
    @Transactional
    @Test
    fun testSaveNewBeer() {
        val beerDTO = BeerDTO(
            name = "Test Beer"
        )

        val responseEntity: ResponseEntity<BeerDTO> = beerController.handlePost(beerDTO)
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.CREATED)
        assertThat(responseEntity.headers.get("Location")).isNotNull

        val locationUUID: List<String>? = responseEntity.headers.location?.path?.split("/")
        val uuid: UUID = UUID.fromString(locationUUID!![4])

        val beer: Beer = beerRepository.findById(uuid).get()
        assertThat(beer).isNotNull
        assertThat(beer.name).isEqualTo(beerDTO.name)
    }

    @Rollback
    @Transactional
    @Test
    fun testUpdateBeer() {
        val beer: Beer = beerRepository.findAll()[0]
        val beerDTO: BeerDTO = beerMapper.toDto(beer)
        beerDTO.id = null
        beerDTO.version = null
        beerDTO.quantity = null

        val beerName = "UPDATED"
        beerDTO.name = beerName

        val beerId = requireNotNull(beer.id) { "Beer ID cannot be null" }
        val responseEntity: ResponseEntity<Void> = beerController.updateById(beerId, beerDTO)
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.NO_CONTENT)

        val updatedBeer = beerRepository.findById(beerId).get()
        assertThat(updatedBeer.name).isEqualTo(beerName)
        assertThat(updatedBeer.quantity).isEqualTo(null)
    }

    @Test
    fun testUpdateBeerNotFound() {
        assertThrows(NotFoundException::class.java) {
            beerController.updateById(UUID.randomUUID(), BeerDTO())
        }
    }

    @Rollback
    @Transactional
    @Test
    fun testDeleteBeerById() {
        val beer: Beer = beerRepository.findAll()[0]
        val beerId = requireNotNull(beer.id) { "Beer ID cannot be null" }

        val responseEntity = beerController.deleteById(beerId)
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.NO_CONTENT)
        assertThat(beerRepository.findById(beerId)).isEmpty
    }

    @Test
    fun testRemoveBeerNotFound() {
        assertThrows(NotFoundException::class.java) {
            beerController.deleteById(UUID.randomUUID())
        }
    }

    @Rollback
    @Transactional
    @Test
    fun testPatchBeer() {
        val beer: Beer = beerRepository.findAll()[0]
        val beerDTO: BeerDTO = beerMapper.toDto(beer)
        val beerDefaultQuantity = beerDTO.quantity
        val beerDefaultVersion = beerDTO.version
        beerDTO.id = null
        beerDTO.version = null
        beerDTO.quantity = null

        val beerName = "UPDATED"
        beerDTO.name = beerName

        val beerId = requireNotNull(beer.id) { "Beer ID cannot be null" }
        val responseEntity: ResponseEntity<Void> = beerController.patchById(beerId, beerDTO)
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.NO_CONTENT)

        val updatedBeer = beerRepository.findById(beerId).get()
        assertThat(updatedBeer.name).isEqualTo(beerName)
        assertThat(updatedBeer.version).isEqualTo(beerDefaultVersion)
        assertThat(updatedBeer.quantity).isEqualTo(beerDefaultQuantity)
    }

    @Test
    fun testPatchBeerNotFound() {
        assertThrows(NotFoundException::class.java) {
            beerController.patchById(UUID.randomUUID(), BeerDTO())
        }
    }

    @Test
    fun testPatchBeerBadName() {
        val testBeer = beerRepository.findAll()[0]
        val beerMap: MutableMap<String, Any> = HashMap()
        beerMap["name"] = "New Name New Name New Name New Name New Name New Name"

        val result = mockMvc.perform(
            patch(BeerController.BEER_PATH_WITH_ID, testBeer.id)
                .with(httpBasic(USERNAME, PASSWORD))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beerMap))
        )
            .andExpect(status().isBadRequest)
            .andReturn()

        println(result.response.contentAsString)
    }

    @Test
    fun testListBeersByName() {
        mockMvc.perform(
            get(BeerController.BASE_BEER_PATH)
                .with(httpBasic(USERNAME, PASSWORD))
                .queryParam("name", "IPA")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.content.size()", equalTo(25)))
    }

    @Test
    fun testListBeersByStyle() {
        mockMvc.perform(
            get(BeerController.BASE_BEER_PATH)
                .with(httpBasic(USERNAME, PASSWORD))
                .queryParam("style", BeerStyle.ALE.toString())
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.content.size()", equalTo(25)))
    }

    @Test
    fun testListBeersByStyleAndNameAndShowInventory() {
        mockMvc.perform(
            get(BeerController.BASE_BEER_PATH)
                .with(httpBasic(USERNAME, PASSWORD))
                .queryParam("name", "ALE")
                .queryParam("style", BeerStyle.ALE.toString())
                .queryParam("showInventory", "TRUE")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.content.size()", equalTo(25)))
            .andExpect(jsonPath("$.content[0].quantity").value(IsNull.nullValue()))
    }

    @Test
    fun testListBeersByStyleAndName() {
        mockMvc.perform(
            get(BeerController.BASE_BEER_PATH)
                .with(httpBasic(USERNAME, PASSWORD))
                .queryParam("name", "ALE")
                .queryParam("style", BeerStyle.ALE.toString())
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.content.size()", equalTo(25)))
            .andExpect(jsonPath("$.content[0].quantity").value(IsNull.notNullValue()))
    }

    @Test
    fun testListBeersByStyleAndNameShowInventoryTruePage2() {
        mockMvc.perform(
            get(BeerController.BASE_BEER_PATH)
                .with(httpBasic(USERNAME, PASSWORD))
                .queryParam("name", "ALE")
                .queryParam("style", BeerStyle.ALE.toString())
                .queryParam("showInventory", "TRUE")
                .queryParam("pageNumber", "2")
                .queryParam("pageSize", "50")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.content.size()", equalTo(50)))
            .andExpect(jsonPath("$.content[0].quantity").value(IsNull.nullValue()))
    }

    @Test
    fun testUpdateBeerBadVersion() {
        val testBeer = beerRepository.findAll()[0]

        val firstTestBeerDTO: BeerDTO = beerMapper.toDto(testBeer)

        firstTestBeerDTO.name = "New Beer Name 1"

        mockMvc.perform(
            put(BeerController.BEER_PATH_WITH_ID, testBeer.id)
                .with(httpBasic(USERNAME, PASSWORD))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(firstTestBeerDTO))
        )
            .andExpect(status().isNoContent)

        val firstUpdatedBeer = beerRepository.findById(testBeer.id!!).get()
        assertThat(firstUpdatedBeer.name).isEqualTo(firstTestBeerDTO.name)

        val secondTestBeerDTO: BeerDTO = beerMapper.toDto(testBeer)
        secondTestBeerDTO.name = "New Beer Name 2"
        mockMvc.perform(
            put(BeerController.BEER_PATH_WITH_ID, testBeer.id)
                .with(httpBasic(USERNAME, PASSWORD))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(secondTestBeerDTO))
        )
            .andExpect(status().isNoContent)

        val secondUpdatedBeer = beerRepository.findById(testBeer.id!!).get()
        assertThat(secondUpdatedBeer.name).isEqualTo(secondTestBeerDTO.name)
    }
}