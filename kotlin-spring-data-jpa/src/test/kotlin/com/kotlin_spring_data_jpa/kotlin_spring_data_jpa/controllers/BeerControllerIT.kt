package com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.controllers

import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.entities.Beer
import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.models.BeerDTO
import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.repositories.BeerRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import java.util.*

@SpringBootTest
class BeerControllerIT {
    @Autowired
    lateinit var beerRepository: BeerRepository

    @Autowired
    lateinit var beerController: BeerController


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

        val beer:Beer = beerRepository.findById(uuid).get()
        assertThat(beer).isNotNull
        assertThat(beer.name).isEqualTo(beerDTO.name)
    }
}