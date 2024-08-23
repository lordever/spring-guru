package com.sdjpa_spring_data_rest.sdjpa_spring_data_rest.repositories

import com.sdjpa_spring_data_rest.sdjpa_spring_data_rest.domain.Beer
import com.sdjpa_spring_data_rest.sdjpa_spring_data_rest.domain.BeerStyle
import org.springframework.data.domain.Page
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import java.util.*


@RepositoryRestResource(path = "beer", collectionResourceRel = "content")
interface BeerRepository: JpaRepository<Beer, UUID> {
    fun findAllByName(name: String?, pageable: Pageable?): Page<Beer?>?

    fun findAllByStyle(style: BeerStyle?, pageable: Pageable?): Page<Beer?>?

    fun findAllByNameAndStyle(name: String?, style: BeerStyle?, pageable: Pageable?): Page<Beer?>?

    fun findByUpc(upc: String?): Beer?
}