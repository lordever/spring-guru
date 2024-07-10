package com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.repositories

import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.entities.Beer
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*


interface BeerRepository: JpaRepository<Beer, UUID> {
}