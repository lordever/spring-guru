package com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.controllers

import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.entities.Beer
import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.mappers.BeerMapper
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

    @Autowired
    lateinit var beerMapper: BeerMapper


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

        val beerName = "UPDATED"
        beerDTO.name = beerName

        val beerId = requireNotNull(beer.id) { "Beer ID cannot be null" }
        val responseEntity: ResponseEntity<Void> = beerController.updateById(beerId, beerDTO)
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.NO_CONTENT)

        val updatedBeer = beerRepository.findById(beerId).get()
        assertThat(updatedBeer.name).isEqualTo(beerName)
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
}