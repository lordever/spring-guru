package com.sdjpa_spring_data_rest.sdjpa_spring_data_rest.boostrap

import com.sdjpa_spring_data_rest.sdjpa_spring_data_rest.domain.Beer
import com.sdjpa_spring_data_rest.sdjpa_spring_data_rest.domain.BeerStyle
import com.sdjpa_spring_data_rest.sdjpa_spring_data_rest.repositories.BeerRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.math.BigInteger
import java.util.*

@Component
class BeerLoader(private val beerRepository: BeerRepository) : CommandLineRunner {

    companion object {
        private val log = LoggerFactory.getLogger(BeerLoader::class.java)
        const val BEER_1_UPC = "0631234200036"
        const val BEER_2_UPC = "9122089364369"
        const val BEER_3_UPC = "0083783375213"
        const val BEER_4_UPC = "4666337557578"
        const val BEER_5_UPC = "8380495518610"
        const val BEER_6_UPC = "5677465691934"
        const val BEER_7_UPC = "5463533082885"
        const val BEER_8_UPC = "5339741428398"
        const val BEER_9_UPC = "1726923962766"
        const val BEER_10_UPC = "8484957731774"
        const val BEER_11_UPC = "6266328524787"
        const val BEER_12_UPC = "7490217802727"
        const val BEER_13_UPC = "8579613295827"
        const val BEER_14_UPC = "2318301340601"
        const val BEER_15_UPC = "9401790633828"
        const val BEER_16_UPC = "4813896316225"
        const val BEER_17_UPC = "3431272499891"
        const val BEER_18_UPC = "2380867498485"
        const val BEER_19_UPC = "4323950503848"
        const val BEER_20_UPC = "4006016803570"
        const val BEER_21_UPC = "9883012356263"
        const val BEER_22_UPC = "0583668718888"
        const val BEER_23_UPC = "9006801347604"
        const val BEER_24_UPC = "0610275742736"
        const val BEER_25_UPC = "6504219363283"
        const val BEER_26_UPC = "7245173761003"
        const val BEER_27_UPC = "0326984155094"
        const val BEER_28_UPC = "1350188843012"
        const val BEER_29_UPC = "0986442492927"
        const val BEER_30_UPC = "8670687641074"
    }

    override fun run(vararg args: String?) {
        loadBeerObjects()
    }

    @Synchronized
    private fun loadBeerObjects() {
        log.debug("Loading initial data. Count is: {}", beerRepository.count())

        if (beerRepository.count() == 0L) {
            val random = Random(System.currentTimeMillis())

            beerRepository.save(
                Beer(
                    name = "Mango Bobs",
                    style = BeerStyle.ALE,
                    upc = BEER_1_UPC,
                    price = BigDecimal(BigInteger.valueOf(random.nextInt(10000).toLong()), 2),
                    quantity = random.nextInt(5000)
                )
            )

            beerRepository.save(
                Beer(
                    name = "Galaxy Cat",
                    style = BeerStyle.PALE_ALE,
                    upc = BEER_2_UPC,
                    price = BigDecimal(BigInteger.valueOf(random.nextInt(10000).toLong()), 2),
                    quantity = random.nextInt(5000)
                )
            )

            beerRepository.save(
                Beer(
                    name = "No Hammers On The Bar",
                    style = BeerStyle.WHEAT,
                    upc = BEER_3_UPC,
                    price = BigDecimal(BigInteger.valueOf(random.nextInt(10000).toLong()), 2),
                    quantity = random.nextInt(5000)
                )
            )

            beerRepository.save(
                Beer(
                    name = "Blessed",
                    style = BeerStyle.STOUT,
                    upc = BEER_4_UPC,
                    price = BigDecimal(BigInteger.valueOf(random.nextInt(10000).toLong()), 2),
                    quantity = random.nextInt(5000)
                )
            )

            beerRepository.save(
                Beer(
                    name = "Adjunct Trail",
                    style = BeerStyle.STOUT,
                    upc = BEER_5_UPC,
                    price = BigDecimal(BigInteger.valueOf(random.nextInt(10000).toLong()), 2),
                    quantity = random.nextInt(5000)
                )
            )

            beerRepository.save(
                Beer(
                    name = "Very GGGreenn",
                    style = BeerStyle.IPA,
                    upc = BEER_6_UPC,
                    price = BigDecimal(BigInteger.valueOf(random.nextInt(10000).toLong()), 2),
                    quantity = random.nextInt(5000)
                )
            )

            beerRepository.save(
                Beer(
                    name = "Double Barrel Hunahpu's",
                    style = BeerStyle.STOUT,
                    upc = BEER_7_UPC,
                    price = BigDecimal(BigInteger.valueOf(random.nextInt(10000).toLong()), 2),
                    quantity = random.nextInt(5000)
                )
            )

            beerRepository.save(
                Beer(
                    name = "Very Hazy",
                    style = BeerStyle.IPA,
                    upc = BEER_8_UPC,
                    price = BigDecimal(BigInteger.valueOf(random.nextInt(10000).toLong()), 2),
                    quantity = random.nextInt(5000)
                )
            )

            beerRepository.save(
                Beer(
                    name = "SR-71",
                    style = BeerStyle.STOUT,
                    upc = BEER_9_UPC,
                    price = BigDecimal(BigInteger.valueOf(random.nextInt(10000).toLong()), 2),
                    quantity = random.nextInt(5000)
                )
            )

            beerRepository.save(
                Beer(
                    name = "Pliny the Younger",
                    style = BeerStyle.IPA,
                    upc = BEER_10_UPC,
                    price = BigDecimal(BigInteger.valueOf(random.nextInt(10000).toLong()), 2),
                    quantity = random.nextInt(5000)
                )
            )

            beerRepository.save(
                Beer(
                    name = "Blessed",
                    style = BeerStyle.STOUT,
                    upc = BEER_11_UPC,
                    price = BigDecimal(BigInteger.valueOf(random.nextInt(10000).toLong()), 2),
                    quantity = random.nextInt(5000)
                )
            )

            beerRepository.save(
                Beer(
                    name = "King Krush",
                    style = BeerStyle.IPA,
                    upc = BEER_12_UPC,
                    price = BigDecimal(BigInteger.valueOf(random.nextInt(10000).toLong()), 2),
                    quantity = random.nextInt(5000)
                )
            )

            beerRepository.save(
                Beer(
                    name = "PBS Porter",
                    style = BeerStyle.PORTER,
                    upc = BEER_13_UPC,
                    price = BigDecimal(BigInteger.valueOf(random.nextInt(10000).toLong()), 2),
                    quantity = random.nextInt(5000)
                )
            )

            beerRepository.save(
                Beer(
                    name = "Pinball Porter",
                    style = BeerStyle.STOUT,
                    upc = BEER_14_UPC,
                    price = BigDecimal(BigInteger.valueOf(random.nextInt(10000).toLong()), 2),
                    quantity = random.nextInt(5000)
                )
            )

            beerRepository.save(
                Beer(
                    name = "Golden Budda",
                    style = BeerStyle.STOUT,
                    upc = BEER_15_UPC,
                    price = BigDecimal(BigInteger.valueOf(random.nextInt(10000).toLong()), 2),
                    quantity = random.nextInt(5000)
                )
            )

            beerRepository.save(
                Beer(
                    name = "Grand Central Red",
                    style = BeerStyle.LAGER,
                    upc = BEER_16_UPC,
                    price = BigDecimal(BigInteger.valueOf(random.nextInt(10000).toLong()), 2),
                    quantity = random.nextInt(5000)
                )
            )

            beerRepository.save(
                Beer(
                    name = "Pac-Man",
                    style = BeerStyle.STOUT,
                    upc = BEER_17_UPC,
                    price = BigDecimal(BigInteger.valueOf(random.nextInt(10000).toLong()), 2),
                    quantity = random.nextInt(5000)
                )
            )

            beerRepository.save(
                Beer(
                    name = "Ro Sham Bo",
                    style = BeerStyle.IPA,
                    upc = BEER_18_UPC,
                    price = BigDecimal(BigInteger.valueOf(random.nextInt(10000).toLong()), 2),
                    quantity = random.nextInt(5000)
                )
            )

            beerRepository.save(
                Beer(
                    name = "Summer Wheatly",
                    style = BeerStyle.WHEAT,
                    upc = BEER_19_UPC,
                    price = BigDecimal(BigInteger.valueOf(random.nextInt(10000).toLong()), 2),
                    quantity = random.nextInt(5000)
                )
            )

            beerRepository.save(
                Beer(
                    name = "Java Jill",
                    style = BeerStyle.LAGER,
                    upc = BEER_20_UPC,
                    price = BigDecimal(BigInteger.valueOf(random.nextInt(10000).toLong()), 2),
                    quantity = random.nextInt(5000)
                )
            )

            beerRepository.save(
                Beer(
                    name = "Bike Trail Pale",
                    style = BeerStyle.PALE_ALE,
                    upc = BEER_21_UPC,
                    price = BigDecimal(BigInteger.valueOf(random.nextInt(10000).toLong()), 2),
                    quantity = random.nextInt(5000)
                )
            )

            beerRepository.save(
                Beer(
                    name = "N.Z.P",
                    style = BeerStyle.IPA,
                    upc = BEER_22_UPC,
                    price = BigDecimal(BigInteger.valueOf(random.nextInt(10000).toLong()), 2),
                    quantity = random.nextInt(5000)
                )
            )

            beerRepository.save(
                Beer(
                    name = "Stawberry Blond",
                    style = BeerStyle.WHEAT,
                    upc = BEER_23_UPC,
                    price = BigDecimal(BigInteger.valueOf(random.nextInt(10000).toLong()), 2),
                    quantity = random.nextInt(5000)
                )
            )

            beerRepository.save(
                Beer(
                    name = "Loco",
                    style = BeerStyle.PORTER,
                    upc = BEER_24_UPC,
                    price = BigDecimal(BigInteger.valueOf(random.nextInt(10000).toLong()), 2),
                    quantity = random.nextInt(5000)
                )
            )

            beerRepository.save(
                Beer(
                    name = "Spocktoberfest",
                    style = BeerStyle.STOUT,
                    upc = BEER_25_UPC,
                    price = BigDecimal(BigInteger.valueOf(random.nextInt(10000).toLong()), 2),
                    quantity = random.nextInt(5000)
                )
            )

            beerRepository.save(
                Beer(
                    name = "Beach Blond Ale",
                    style = BeerStyle.ALE,
                    upc = BEER_26_UPC,
                    price = BigDecimal(BigInteger.valueOf(random.nextInt(10000).toLong()), 2),
                    quantity = random.nextInt(5000)
                )
            )

            beerRepository.save(
                Beer(
                    name = "Bimini Twist IPA",
                    style = BeerStyle.IPA,
                    upc = BEER_27_UPC,
                    price = BigDecimal(BigInteger.valueOf(random.nextInt(10000).toLong()), 2),
                    quantity = random.nextInt(5000)
                )
            )

            beerRepository.save(
                Beer(
                    name = "Rod Bender Red Ale",
                    style = BeerStyle.ALE,
                    upc = BEER_28_UPC,
                    price = BigDecimal(BigInteger.valueOf(random.nextInt(10000).toLong()), 2),
                    quantity = random.nextInt(5000)
                )
            )

            beerRepository.save(
                Beer(
                    name = "Floating Dock",
                    style = BeerStyle.SAISON,
                    upc = BEER_29_UPC,
                    price = BigDecimal(BigInteger.valueOf(random.nextInt(10000).toLong()), 2),
                    quantity = random.nextInt(5000)
                )
            )

            beerRepository.save(
                Beer(
                    name = "El Hefe",
                    style = BeerStyle.WHEAT,
                    upc = BEER_30_UPC,
                    price = BigDecimal(BigInteger.valueOf(random.nextInt(10000).toLong()), 2),
                    quantity = random.nextInt(5000)
                )
            )

            log.debug("Beer Records loaded: {}", beerRepository.count())
        }
    }
}