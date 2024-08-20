package com.sdjpa_spring_data_rest.sdjpa_spring_data_rest.repositories

import com.sdjpa_spring_data_rest.sdjpa_spring_data_rest.domain.Beer
import com.sdjpa_spring_data_rest.sdjpa_spring_data_rest.domain.BeerStyle
import org.springframework.data.domain.Page
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import java.util.*


@RepositoryRestResource(path = "beer", collectionResourceRel = "beer")
interface BeerRepository: JpaRepository<Beer, UUID> {
    fun findAllByName(beerName: String?, pageable: Pageable?): Page<Beer?>?

    fun findAllByStyle(beerStyle: BeerStyle?, pageable: Pageable?): Page<Beer?>?

    fun findAllByNameAndStyle(beerName: String?, beerStyle: BeerStyle?, pageable: Pageable?): Page<Beer?>?

    fun findByUpc(upc: String?): Beer?
}