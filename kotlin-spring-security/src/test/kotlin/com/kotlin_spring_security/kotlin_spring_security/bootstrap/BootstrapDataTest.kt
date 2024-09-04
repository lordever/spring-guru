package com.kotlin_spring_security.kotlin_spring_security.bootstrap

import com.kotlin_spring_security.kotlin_spring_security.boostrap.BootstrapData
import com.kotlin_spring_security.kotlin_spring_security.repositories.BeerRepository
import com.kotlin_spring_security.kotlin_spring_security.repositories.CustomerRepository
import com.kotlin_spring_security.kotlin_spring_security.services.BeerCsvService
import com.kotlin_spring_security.kotlin_spring_security.services.BeerCsvServiceImpl
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.assertj.core.api.Assertions.assertThat
import org.springframework.context.annotation.Import

@DataJpaTest
@Import(BeerCsvServiceImpl::class)
class BootstrapDataTest {
    @Autowired
    lateinit var beerRepository: BeerRepository

    @Autowired
    lateinit var customerRepository: CustomerRepository

    @Autowired
    lateinit var beerCsvService: BeerCsvService

    lateinit var bootstrapData: BootstrapData

    @BeforeEach
    fun setUp() {
        bootstrapData = BootstrapData(beerRepository, beerCsvService, customerRepository)
    }

    @Test
    fun testRun(){
        bootstrapData.run(null)

        assertThat(beerRepository.count()).isEqualTo(2413)
        assertThat(customerRepository.count()).isEqualTo(4)
    }
}