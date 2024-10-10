package com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.bootstrap

import com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.domain.Beer
import com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.domain.Customer
import com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.repositories.BeerRepository
import com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.repositories.CustomerRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.LocalDateTime

@Component
class BootstrapData(
    var beerRepository: BeerRepository,
    var customerRepository: CustomerRepository
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        loadBeerData()
        loadCustomerData()

        beerRepository.count().subscribe { count -> println("The beer count is $count") }
        customerRepository.count().subscribe { count -> println("The customer count is $count") }
    }

    fun loadBeerData() {
        beerRepository.count().subscribe { count ->
            run {
                if (count == 0L) {
                    val beer1 = Beer(
                        name = "Galaxy Cat",
                        style = "Pale Ale",
                        upc = "12356",
                        price = BigDecimal("12.99"),
                        quantity = 122
                    )

                    val beer2 = Beer(
                        name = "Crank",
                        style = "Pale Ale",
                        upc = "12356222",
                        price = BigDecimal("11.99"),
                        quantity = 392
                    )

                    val beer3 = Beer(
                        name = "Sunshine City",
                        style = "IPA",
                        upc = "12356",
                        price = BigDecimal("13.99"),
                        quantity = 144
                    )

                    beerRepository.save(beer1).subscribe()
                    beerRepository.save(beer2).subscribe()
                    beerRepository.save(beer3).subscribe()
                }
            }
        }
    }

    fun loadCustomerData() {
        customerRepository.count().subscribe { count ->
            if (count == 0L) {
                val customer1 = Customer(
                    name = "John Doe",
                    createdDate = LocalDateTime.now(),
                    lastModifiedDate = LocalDateTime.now()
                )

                val customer2 = Customer(
                    name = "Jane Smith",
                    createdDate = LocalDateTime.now(),
                    lastModifiedDate = LocalDateTime.now()
                )

                val customer3 = Customer(
                    name = "Michael Johnson",
                    createdDate = LocalDateTime.now(),
                    lastModifiedDate = LocalDateTime.now()
                )

                val customer4 = Customer(
                    name = "Emily Davis",
                    createdDate = LocalDateTime.now(),
                    lastModifiedDate = LocalDateTime.now()
                )

                val customer5 = Customer(
                    name = "David Brown",
                    createdDate = LocalDateTime.now(),
                    lastModifiedDate = LocalDateTime.now()
                )

                customerRepository.save(customer1).subscribe()
                customerRepository.save(customer2).subscribe()
                customerRepository.save(customer3).subscribe()
                customerRepository.save(customer4).subscribe()
                customerRepository.save(customer5).subscribe()
            }
        }
    }
}