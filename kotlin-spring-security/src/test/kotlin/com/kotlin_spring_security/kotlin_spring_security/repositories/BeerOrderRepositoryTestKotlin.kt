package com.kotlin_spring_security.kotlin_spring_security.repositories

import com.kotlin_spring_security.kotlin_spring_security.entities.Beer
import com.kotlin_spring_security.kotlin_spring_security.entities.BeerOrder
import com.kotlin_spring_security.kotlin_spring_security.entities.BeerOrderShipment
import com.kotlin_spring_security.kotlin_spring_security.entities.Customer
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class BeerOrderRepositoryTestKotlin {
    @Autowired
    lateinit var beerOrderRepository: BeerOrderRepository

    @Autowired
    lateinit var customerRepository: CustomerRepository

    @Autowired
    lateinit var beerRepository: BeerRepository

    var testCustomer: Customer = Customer()
    var testBeer: Beer = Beer()

    @BeforeEach
    fun setUp() {
        testCustomer = customerRepository.findAll()[0]
        testBeer = beerRepository.findAll()[0]
    }

    @Transactional
    @Test
    fun testBeerOrders() {
        val beerOrder = BeerOrder(
            customerRef = "Test Order"
        )

        beerOrder.assignCustomer(testCustomer)
        beerOrder.assignBeerOrderShipment(
            BeerOrderShipment(
                trackingNumber = "12345r"
            )
        )

        val savedBeerOrder = beerOrderRepository.save(beerOrder)

        println(savedBeerOrder.customerRef)
    }
}