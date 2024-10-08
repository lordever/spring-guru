package com.kotlin_spring_mysql.kotlin_spring_mysql.repositories

import com.kotlin_spring_mysql.kotlin_spring_mysql.entities.BeerOrder
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface BeerOrderRepository: JpaRepository<BeerOrder, UUID>{
}