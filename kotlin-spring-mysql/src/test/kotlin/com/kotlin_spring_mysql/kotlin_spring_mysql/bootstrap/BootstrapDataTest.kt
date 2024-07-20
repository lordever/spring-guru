package com.kotlin_spring_mysql.kotlin_spring_mysql.bootstrap

import com.kotlin_spring_mysql.kotlin_spring_mysql.boostrap.BootstrapData
import com.kotlin_spring_mysql.kotlin_spring_mysql.repositories.BeerRepository
import com.kotlin_spring_mysql.kotlin_spring_mysql.repositories.CustomerRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.assertj.core.api.Assertions.assertThat

@DataJpaTest
class BootstrapDataTest {
    @Autowired
    lateinit var beerRepository: BeerRepository

    @Autowired
    lateinit var customerRepository: CustomerRepository

    lateinit var bootstrapData: BootstrapData

    @BeforeEach
    fun setUp() {
        bootstrapData = BootstrapData(beerRepository, customerRepository)
    }

    @Test
    fun testRun(){
        bootstrapData.run(null)

        assertThat(beerRepository.count()).isEqualTo(3)
        assertThat(customerRepository.count()).isEqualTo(4)
    }
}