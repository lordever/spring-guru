package com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.entities

import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.models.BeerStyle
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Entity
data class Beer(
    var quantity: Int? = null,
    var createDate: LocalDateTime? = null,
    var updateDate: LocalDateTime? = null,

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    var id: UUID? = null,

    @Version
    var version: Int? = null,

    @field:NotNull
    var price: BigDecimal? = null,

    @field:NotBlank
    @field:NotNull
    var name: String? = null,

    @field:NotNull
    var style: BeerStyle? = null,

    @field:NotBlank
    @field:NotNull
    var upc: String? = null,
)