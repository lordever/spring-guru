package com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.entities

import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.models.BeerStyle
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Entity
data class Beer(
    @Id
    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length=36,columnDefinition = "varchar", updatable = false, nullable = false)
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