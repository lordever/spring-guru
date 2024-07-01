package com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.services

import com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.models.Beer
import com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.models.BeerStyle
import org.springframework.stereotype.Service
import mu.KotlinLogging
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@Service
class BeerServiceImpl : BeerService {
    private val log = KotlinLogging.logger {}
    private var beerMap: MutableMap<UUID, Beer> = mutableMapOf()

    init {
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

        beerMap[beer1.id] = beer1
        beerMap[beer2.id] = beer2
        beerMap[beer3.id] = beer3
    }

    override fun getBeerById(id: UUID): Beer? {
        log.debug { "Get Beer Id in service was called" }

        return beerMap[id]
    }

    override fun listBeer(): List<Beer> {
        return beerMap.values.toList()
    }

    override fun save(beer: Beer): Beer {
        val newBeer = Beer(
            version = beer.version,
            name = beer.name,
            style = beer.style,
            upc = beer.upc,
            price = beer.price,
            quantity = beer.quantity,
            createDate = LocalDateTime.now(),
            updateDate = LocalDateTime.now()
        )

        beerMap[newBeer.id] = newBeer

        return newBeer
    }

    override fun updateById(id: UUID, newBeer: Beer) {
        val existingBeer = beerMap[id]

        if (existingBeer != null) {
            existingBeer.name = newBeer.name
            existingBeer.price = newBeer.price
            existingBeer.quantity = newBeer.quantity
            existingBeer.style = newBeer.style
            existingBeer.upc = newBeer.upc
            existingBeer.version = newBeer.version
            existingBeer.updateDate = LocalDateTime.now()

            beerMap[id] = existingBeer
        } else {
            log.debug { "Beer $id wasn't found" }
        }
    }

    override fun patchById(id: UUID, newBeer: Beer) {
        val existingBeer = beerMap[id]

        if (existingBeer != null) {
            if (newBeer.name != null) {
                existingBeer.name = newBeer.name
            }
            if (newBeer.price != null) {
                existingBeer.price = newBeer.price
            }
            if (newBeer.quantity != null) {
                existingBeer.quantity = newBeer.quantity
            }
            if (newBeer.style != null) {
                existingBeer.style = newBeer.style
            }
            if (newBeer.upc != null) {
                existingBeer.upc = newBeer.upc
            }
            if (newBeer.version != null) {
                existingBeer.version = newBeer.version
            }

            existingBeer.updateDate = LocalDateTime.now()

            beerMap[id] = existingBeer
        } else {
            log.debug { "Beer $id wasn't found" }
        }
    }

    override fun deleteById(id: UUID) {
        beerMap.remove(id)
    }
}