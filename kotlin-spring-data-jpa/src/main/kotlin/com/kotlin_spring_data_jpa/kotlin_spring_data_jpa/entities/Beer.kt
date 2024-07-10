package com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.entities

import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.models.BeerStyle
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Version
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Entity
data class Beer(
    @Id
    val id: UUID? = null,

    @Version
    val version: Int? = null,

    val beerName: String? = null,
    val beerStyle: BeerStyle? = null,
    val upc: String? = null,
    val quantityOnHand: Int? = null,
    val price: BigDecimal? = null,
    val createdDate: LocalDateTime? = null,
    val updateDate: LocalDateTime? = null
)