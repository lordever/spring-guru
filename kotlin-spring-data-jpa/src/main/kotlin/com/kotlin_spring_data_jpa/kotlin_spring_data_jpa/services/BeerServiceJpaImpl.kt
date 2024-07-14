package com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.services

import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.mappers.BeerMapper
import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.models.BeerDTO
import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.repositories.BeerRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import java.util.*

@Primary
@Service
class BeerServiceJpaImpl(
    private val beerRepository: BeerRepository,
    private val beerMapper: BeerMapper
) : BeerService {
    override fun getBeerById(id: UUID): BeerDTO? =
        beerRepository.findById(id).map(beerMapper::toDto).orElse(null)

    override fun listBeer(): List<BeerDTO> = beerRepository.findAll().map(beerMapper::toDto)

    override fun save(beerDTO: BeerDTO): BeerDTO =
        beerMapper
            .toDto(
                beerRepository.save(
                    beerMapper.toBeer(beerDTO)
                )
            )

    override fun updateById(id: UUID, newBeerDTO: BeerDTO) =
        beerRepository.findById(id).ifPresent { foundBeer ->
            foundBeer.apply {
                name = newBeerDTO.name
                style = newBeerDTO.style
                upc = newBeerDTO.upc
                price = newBeerDTO.price
            }

            beerRepository.save(foundBeer)
        }

    override fun patchById(id: UUID, newBeerDTO: BeerDTO) {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: UUID) {
        TODO("Not yet implemented")
    }
}