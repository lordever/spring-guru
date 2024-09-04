package com.kotlin_spring_security.kotlin_spring_security.boostrap

import com.kotlin_spring_security.kotlin_spring_security.entities.Beer
import com.kotlin_spring_security.kotlin_spring_security.entities.Customer
import com.kotlin_spring_security.kotlin_spring_security.models.BeerCSVRecord
import com.kotlin_spring_security.kotlin_spring_security.models.BeerStyle
import com.kotlin_spring_security.kotlin_spring_security.repositories.BeerRepository
import com.kotlin_spring_security.kotlin_spring_security.repositories.CustomerRepository
import com.kotlin_spring_security.kotlin_spring_security.services.BeerCsvService
import com.kotlin_spring_security.kotlin_spring_security.services.BeerService
import org.apache.commons.lang3.StringUtils
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.ResourceUtils
import java.io.File
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Component
class BootstrapData(
    var beerRepository: BeerRepository,
    var beerCsvService: BeerCsvService,
    var customerRepository: CustomerRepository,
) : CommandLineRunner {

    @Transactional
    override fun run(vararg args: String?) {
        loadBeerData()
        loadCsvData()
        loadCustomerData()
    }

    private fun loadBeerData() {
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

    private fun loadCsvData() {
        if (beerRepository.count() < 10) {
            val file: File = ResourceUtils.getFile("classpath:csvdata/beers.csv")

            val recs: List<BeerCSVRecord> = beerCsvService.convertCsv(file)

            recs.forEach { beerCSVRecord ->
                val beerStyle = when (beerCSVRecord.style) {
                    "American Pale Lager" -> BeerStyle.LAGER
                    "American Pale Ale (APA)", "American Black Ale", "Belgian Dark Ale", "American Blonde Ale" -> BeerStyle.ALE
                    "American IPA", "American Double / Imperial IPA", "Belgian IPA" -> BeerStyle.IPA
                    "American Porter" -> BeerStyle.PORTER
                    "Oatmeal Stout", "American Stout" -> BeerStyle.STOUT
                    "Saison / Farmhouse Ale" -> BeerStyle.SAISON
                    "Fruit / Vegetable Beer", "Winter Warmer", "Berliner Weissbier" -> BeerStyle.WHEAT
                    "English Pale Ale" -> BeerStyle.PALE_ALE
                    else -> BeerStyle.PILSNER
                }

                beerRepository.save(
                    Beer(
                        name = StringUtils.abbreviate(beerCSVRecord.beer, 50),
                        style = beerStyle,
                        price = BigDecimal.TEN,
                        upc = beerCSVRecord.row.toString(),
                        quantity = beerCSVRecord.count
                    )
                )
            }
        }
    }

    private fun loadCustomerData() {
        if (customerRepository.count() == 0L) {
            val customer1 = Customer(
                id = UUID.randomUUID(),
                name = "John Doe",
                version = 1
            )

            val customer2 = Customer(
                id = UUID.randomUUID(),
                name = "Jane Smith",
                version = 1
            )

            val customer3 = Customer(
                id = UUID.randomUUID(),
                name = "Alice Johnson",
                version = 1
            )

            val customer4 = Customer(
                id = UUID.randomUUID(),
                name = "Bob Brown",
                version = 1
            )

            customerRepository.save(customer1)
            customerRepository.save(customer2)
            customerRepository.save(customer3)
            customerRepository.save(customer4)
        }
    }
}