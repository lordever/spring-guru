package com.kotlin_spring_mysql.kotlin_spring_mysql.repositories

import com.kotlin_spring_mysql.kotlin_spring_mysql.entities.Beer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BeerRepository: JpaRepository<Beer, UUID> {
    fun findAllByNameIsLikeIgnoreCase(name: String): List<Beer>
}