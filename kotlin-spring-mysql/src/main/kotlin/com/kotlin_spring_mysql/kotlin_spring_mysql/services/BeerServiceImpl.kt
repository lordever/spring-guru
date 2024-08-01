package com.kotlin_spring_mysql.kotlin_spring_mysql.services

import com.kotlin_spring_mysql.kotlin_spring_mysql.models.BeerDTO
import com.kotlin_spring_mysql.kotlin_spring_mysql.models.BeerStyle
import org.springframework.stereotype.Service
import mu.KotlinLogging
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@Service
class BeerServiceImpl : BeerService {
    private val log = KotlinLogging.logger {}
    private var beerDTOMap: MutableMap<UUID, BeerDTO> = mutableMapOf()

    init {
        val beerDTO1 = BeerDTO(
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

        val beerDTO2 = BeerDTO(
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

        val beerDTO3 = BeerDTO(
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

        beerDTO1.id?.let { beerDTOMap[it] = beerDTO1 }
        beerDTO2.id?.let { beerDTOMap[it] = beerDTO2 }
        beerDTO3.id?.let { beerDTOMap[it] = beerDTO3 }
    }

    override fun getBeerById(id: UUID): BeerDTO? {
        log.debug { "Get Beer Id in service was called" }

        return beerDTOMap[id]
    }

    override fun listBeer(name: String?, style: BeerStyle?): List<BeerDTO> {
        return beerDTOMap.values.toList()
    }

    override fun save(beerDTO: BeerDTO): BeerDTO {
        val newBeerDTO = BeerDTO(
            id = UUID.randomUUID(),
            version = beerDTO.version,
            name = beerDTO.name,
            style = beerDTO.style,
            upc = beerDTO.upc,
            price = beerDTO.price,
            quantity = beerDTO.quantity,
            createDate = LocalDateTime.now(),
            updateDate = LocalDateTime.now()
        )

        newBeerDTO.id?.let { beerDTOMap[it] = newBeerDTO }

        return newBeerDTO
    }

    override fun updateById(id: UUID, newBeerDTO: BeerDTO): BeerDTO? {
        val existingBeer = beerDTOMap[id]

        if (existingBeer != null) {
            existingBeer.name = newBeerDTO.name
            existingBeer.price = newBeerDTO.price
            existingBeer.quantity = newBeerDTO.quantity
            existingBeer.style = newBeerDTO.style
            existingBeer.upc = newBeerDTO.upc
            existingBeer.version = newBeerDTO.version
            existingBeer.updateDate = LocalDateTime.now()

            beerDTOMap[id] = existingBeer
        } else {
            log.debug { "Beer $id wasn't found" }
        }

        return existingBeer
    }

    override fun patchById(id: UUID, newBeerDTO: BeerDTO): BeerDTO? {
        val existingBeer = beerDTOMap[id]

        if (existingBeer != null) {
            if (newBeerDTO.name != null) {
                existingBeer.name = newBeerDTO.name
            }
            if (newBeerDTO.price != null) {
                existingBeer.price = newBeerDTO.price
            }
            if (newBeerDTO.quantity != null) {
                existingBeer.quantity = newBeerDTO.quantity
            }
            if (newBeerDTO.style != null) {
                existingBeer.style = newBeerDTO.style
            }
            if (newBeerDTO.upc != null) {
                existingBeer.upc = newBeerDTO.upc
            }
            if (newBeerDTO.version != null) {
                existingBeer.version = newBeerDTO.version
            }

            existingBeer.updateDate = LocalDateTime.now()

            beerDTOMap[id] = existingBeer
            return beerDTOMap[id]
        } else {
            log.debug { "Beer $id wasn't found" }
            return null
        }
    }

    override fun deleteById(id: UUID): Boolean {
        beerDTOMap.remove(id)

        return true
    }
}