package com.kotlin_spring_mysql.kotlin_spring_mysql.boostrap

import com.kotlin_spring_mysql.kotlin_spring_mysql.entities.Beer
import com.kotlin_spring_mysql.kotlin_spring_mysql.entities.Customer
import com.kotlin_spring_mysql.kotlin_spring_mysql.models.BeerStyle
import com.kotlin_spring_mysql.kotlin_spring_mysql.repositories.BeerRepository
import com.kotlin_spring_mysql.kotlin_spring_mysql.repositories.CustomerRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Component
class BootstrapData(
    var beerRepository: BeerRepository,
    var customerRepository: CustomerRepository
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        loadBeerData()
        loadCustomerData()
    }

    fun loadBeerData() {
        if (beerRepository.count() == 0L) {
            val beer1 = Beer(
                id = UUID.randomUUID(),
                version = 1,
                name = "Galaxy Cat",
                style = BeerStyle.PALE_ALE,
                upc = "12356",
                price = BigDecimal("12.99"),
                quantity = 122,
                createDate = LocalDateTime.now(),
                updateDate = LocalDateTime.now()
            )

            val beer2 = Beer(
                id = UUID.randomUUID(),
                version = 1,
                name = "Crank",
                style = BeerStyle.PALE_ALE,
                upc = "12356222",
                price = BigDecimal("11.99"),
                quantity = 392,
                createDate = LocalDateTime.now(),
                updateDate = LocalDateTime.now()
            )

            val beer3 = Beer(
                id = UUID.randomUUID(),
                version = 1,
                name = "Sunshine City",
                style = BeerStyle.IPA,
                upc = "12356",
                price = BigDecimal("13.99"),
                quantity = 144,
                createDate = LocalDateTime.now(),
                updateDate = LocalDateTime.now()
            )

            beerRepository.save(beer1)
            beerRepository.save(beer2)
            beerRepository.save(beer3)
        }
    }

    fun loadCustomerData() {
        if (customerRepository.count() == 0L) {
            val customer1 = Customer(
                id = UUID.randomUUID(),
                name = "John Doe",
                version = 1,
                createdDate = LocalDateTime.now(),
                lastModifiedDate = LocalDateTime.now()
            )

            val customer2 = Customer(
                id = UUID.randomUUID(),
                name = "Jane Smith",
                version = 1,
                createdDate = LocalDateTime.now(),
                lastModifiedDate = LocalDateTime.now()
            )

            val customer3 = Customer(
                id = UUID.randomUUID(),
                name = "Alice Johnson",
                version = 1,
                createdDate = LocalDateTime.now(),
                lastModifiedDate = LocalDateTime.now()
            )

            val customer4 = Customer(
                id = UUID.randomUUID(),
                name = "Bob Brown",
                version = 1,
                createdDate = LocalDateTime.now(),
                lastModifiedDate = LocalDateTime.now()
            )

            customerRepository.save(customer1)
            customerRepository.save(customer2)
            customerRepository.save(customer3)
            customerRepository.save(customer4)
        }
    }
}