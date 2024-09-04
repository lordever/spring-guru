package com.kotlin_spring_security.kotlin_spring_security.repositories

import com.kotlin_spring_security.kotlin_spring_security.entities.BeerOrder
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface BeerOrderRepository: JpaRepository<BeerOrder, UUID>{
}