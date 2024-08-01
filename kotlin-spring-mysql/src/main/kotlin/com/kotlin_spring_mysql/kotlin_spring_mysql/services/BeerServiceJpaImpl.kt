package com.kotlin_spring_mysql.kotlin_spring_mysql.services

import com.kotlin_spring_mysql.kotlin_spring_mysql.entities.Beer
import com.kotlin_spring_mysql.kotlin_spring_mysql.mappers.BeerMapper
import com.kotlin_spring_mysql.kotlin_spring_mysql.models.BeerDTO
import com.kotlin_spring_mysql.kotlin_spring_mysql.models.BeerStyle
import com.kotlin_spring_mysql.kotlin_spring_mysql.repositories.BeerRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import java.util.*
import kotlin.collections.ArrayList


@Primary
@Service
class BeerServiceJpaImpl(
    private val beerRepository: BeerRepository,
    private val beerMapper: BeerMapper
) : BeerService {
    override fun getBeerById(id: UUID): BeerDTO? =
        beerRepository.findById(id).map(beerMapper::toDto).orElse(null)

    override fun listBeer(name: String?, style: BeerStyle?): List<BeerDTO> {
        val beerList: List<Beer> =
            if (StringUtils.hasText(name) && name != null) {
                listBeersByName(name)
            } else if (!StringUtils.hasText(name) && style != null) {
                listBeersByStyle(style)
            } else {
                beerRepository.findAll()
            }

        return beerList.map(beerMapper::toDto)
    }

    private fun listBeersByName(name: String): List<Beer> {
        return beerRepository.findAllByNameIsLikeIgnoreCase("%${name}%")
    }

    private fun listBeersByStyle(style: BeerStyle): List<Beer> {
        return beerRepository.findAllByStyle(style)
    }

    override fun save(beerDTO: BeerDTO): BeerDTO =
        beerMapper
            .toDto(
                beerRepository.save(
                    beerMapper.toBeer(beerDTO)
                )
            )

    override fun updateById(id: UUID, newBeerDTO: BeerDTO): BeerDTO? {
        return beerRepository.findById(id).map { foundBeer ->
            foundBeer.apply {
                name = newBeerDTO.name
                style = newBeerDTO.style
                upc = newBeerDTO.upc
                price = newBeerDTO.price
                quantity = newBeerDTO.quantity
            }
            beerMapper.toDto(beerRepository.save(foundBeer))
        }.orElse(null)
    }

    override fun patchById(id: UUID, newBeerDTO: BeerDTO): BeerDTO? {
        return beerRepository.findById(id).map { foundBeer ->
            if (StringUtils.hasText(newBeerDTO.name)) {
                foundBeer.name = newBeerDTO.name
            }

            if (newBeerDTO.upc != null) {
                foundBeer.upc = newBeerDTO.upc
            }

            if (newBeerDTO.price != null) {
                foundBeer.price = newBeerDTO.price
            }

            if (newBeerDTO.style != null) {
                foundBeer.style = newBeerDTO.style
            }

            if (newBeerDTO.quantity != null) {
                foundBeer.quantity = newBeerDTO.quantity
            }

            beerMapper.toDto(beerRepository.save(foundBeer))
        }.orElse(null)
    }

    override fun deleteById(id: UUID): Boolean {
        if (beerRepository.existsById(id)) {
            beerRepository.deleteById(id)
            return true
        }

        return false
    }
}