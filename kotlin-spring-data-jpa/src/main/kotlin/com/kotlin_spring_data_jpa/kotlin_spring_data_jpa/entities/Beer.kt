package com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.entities

import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.models.BeerStyle
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Entity
data class Beer(
    var name: String? = null,
    var beerStyle: BeerStyle? = null,
    var upc: String? = null,
    var quantityOnHand: Int? = null,
    var price: BigDecimal? = null,
    var createDate: LocalDateTime? = null,
    var updateDate: LocalDateTime? = null,
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    var id: UUID? = null,
    @Version
    var version: Int? = null
)