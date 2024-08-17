package com.kotlin_spring_mysql.kotlin_spring_mysql.services

import com.kotlin_spring_mysql.kotlin_spring_mysql.entities.Beer
import com.kotlin_spring_mysql.kotlin_spring_mysql.mappers.BeerMapper
import com.kotlin_spring_mysql.kotlin_spring_mysql.models.BeerDTO
import com.kotlin_spring_mysql.kotlin_spring_mysql.models.BeerStyle
import com.kotlin_spring_mysql.kotlin_spring_mysql.repositories.BeerRepository
import org.springframework.context.annotation.Primary
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import java.util.*


@Primary
@Service
class BeerServiceJpaImpl(
    private val beerRepository: BeerRepository, private val beerMapper: BeerMapper
) : BeerService {

    companion object {
        const val DEFAULT_PAGE = 0
        const val DEFAULT_PAGE_SIZE = 25
    }

    override fun getBeerById(id: UUID): BeerDTO? = beerRepository.findById(id).map(beerMapper::toDto).orElse(null)

    override fun listBeer(
        name: String?, style: BeerStyle?, showInventory: Boolean, pageNumber: Int?, pageSize: Int?
    ): Page<BeerDTO> {

        val pageRequest = buildPageRequest(pageNumber, pageSize)

        val beerPage: Page<Beer> =
            if ((StringUtils.hasText(name) && name != null) && style == null) {
                listBeersByName(name, pageRequest)
            } else if (!StringUtils.hasText(name) && style != null) {
                listBeersByStyle(style, pageRequest)
            } else if ((name != null && StringUtils.hasText(name)) && style != null) {
                listBeersByNameAndStyle(name, style, pageRequest)
            } else {
                beerRepository.findAll(pageRequest)
            }

        if (showInventory) {
            beerPage.forEach { beer -> beer.quantity = null }
        }

        return beerPage.map(beerMapper::toDto)
    }

    private fun buildPageRequest(pageNumber: Int?, pageSize: Int?): PageRequest {
        var queryPageNumber: Int = DEFAULT_PAGE

        if (pageNumber != null) {
            if (pageNumber > 0) {
                queryPageNumber = pageNumber - 1
            }
        }

        val queryPageSize: Int
        if (pageSize == null) {
            queryPageSize = DEFAULT_PAGE_SIZE
        } else {
            if (pageSize > 1000) {
                queryPageSize = 1000
            } else {
                queryPageSize = pageSize
            }
        }

        val sort: Sort = Sort.by(Sort.Order.asc("name"))

        return PageRequest.of(queryPageNumber, queryPageSize, sort)
    }
    private fun listBeersByName(name: String, pageable: Pageable?): Page<Beer> {
        return beerRepository.findAllByNameIsLikeIgnoreCase("%${name}%", pageable)
    }

    private fun listBeersByStyle(style: BeerStyle, pageable: Pageable?): Page<Beer> {
        return beerRepository.findAllByStyle(style, pageable)
    }

    private fun listBeersByNameAndStyle(name: String, style: BeerStyle, pageable: Pageable?): Page<Beer> {
        return beerRepository.findAllByNameIsLikeIgnoreCaseAndStyle("%${name}%", style, pageable)
    }

    override fun save(beerDTO: BeerDTO): BeerDTO = beerMapper.toDto(
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
                version = newBeerDTO.version
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