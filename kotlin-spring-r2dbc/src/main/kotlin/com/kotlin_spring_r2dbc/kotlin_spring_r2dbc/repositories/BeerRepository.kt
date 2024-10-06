package com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.repositories

import com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.domain.Beer
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface BeerRepository: ReactiveCrudRepository<Beer, Int> {
}