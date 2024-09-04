package com.kotlin_spring_security.kotlin_spring_security.repositories

import com.kotlin_spring_security.kotlin_spring_security.entities.Beer
import com.kotlin_spring_security.kotlin_spring_security.models.BeerStyle
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BeerRepository : JpaRepository<Beer, UUID> {
    fun findAllByNameIsLikeIgnoreCase(name: String, pageable: Pageable?): Page<Beer>
    fun findAllByStyle(style: BeerStyle, pageable: Pageable?): Page<Beer>
    fun findAllByNameIsLikeIgnoreCaseAndStyle(name: String, style: BeerStyle, pageable: Pageable?): Page<Beer>
}