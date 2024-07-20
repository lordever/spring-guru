package com.kotlin_spring_mysql.kotlin_spring_mysql.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.kotlin_spring_mysql.kotlin_spring_mysql.entities.Beer
import com.kotlin_spring_mysql.kotlin_spring_mysql.mappers.BeerMapper
import com.kotlin_spring_mysql.kotlin_spring_mysql.models.BeerDTO
import com.kotlin_spring_mysql.kotlin_spring_mysql.repositories.BeerRepository
import io.mockk.every
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.annotation.Rollback
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.context.WebApplicationContext
import java.util.*

@SpringBootTest
class BeerControllerIT {
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
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build()
    }

    @Test
    fun testListBeers() {
        val dtos: List<BeerDTO> = beerController.listBeers()

        assertThat(dtos.size).isEqualTo(3)
    }

    @Rollback
    @Transactional
    @Test
    fun testEmptyList() {
        beerRepository.deleteAll()

        val dtos: List<BeerDTO> = beerController.listBeers()

        assertThat(dtos.size).isEqualTo(0)
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

    @Rollback
    @Transactional
    @Test
    fun testPatchBeerBadName() {
        val testBeer = beerRepository.findAll()[0]
        val beerMap: MutableMap<String, Any> = HashMap()
        beerMap["name"] = "New Name New Name New Name New Name New Name New Name"

        val result = mockMvc.perform(
            patch(BeerController.BEER_PATH_WITH_ID, testBeer.id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beerMap))
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.length()", equalTo(5)))
            .andReturn()

        println(result.response.contentAsString)
    }
}