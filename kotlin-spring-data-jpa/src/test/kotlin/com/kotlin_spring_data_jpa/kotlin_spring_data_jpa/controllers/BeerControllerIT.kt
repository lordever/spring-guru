package com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.controllers

import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.models.BeerDTO
import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.repositories.BeerRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
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
}