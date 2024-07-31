package com.kotlin_spring_mysql.kotlin_spring_mysql.bootstrap

import com.kotlin_spring_mysql.kotlin_spring_mysql.boostrap.BootstrapData
import com.kotlin_spring_mysql.kotlin_spring_mysql.repositories.BeerRepository
import com.kotlin_spring_mysql.kotlin_spring_mysql.repositories.CustomerRepository
import com.kotlin_spring_mysql.kotlin_spring_mysql.services.BeerCsvService
import com.kotlin_spring_mysql.kotlin_spring_mysql.services.BeerCsvServiceImpl
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