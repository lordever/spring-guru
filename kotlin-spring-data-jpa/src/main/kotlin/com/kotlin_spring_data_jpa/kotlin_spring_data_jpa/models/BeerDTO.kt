package com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.models

import jakarta.persistence.Column
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class BeerDTO(
    var id: UUID? = null,
    var version: Int? = null,
    var quantity: Int? = null,

    @field:NotBlank
    @field:NotNull
    @field:Column(length = 50)
    @field:Size(max = 50)
    var name: String? = null,

    @field:NotBlank
    @field:NotNull
    @field:Size(max = 255)
    var upc: String? = null,

    @field:NotNull
    var style: BeerStyle? = null,

    @field:NotNull
    var price: BigDecimal? = null,
    var createDate: LocalDateTime = LocalDateTime.now(),
    var updateDate: LocalDateTime = LocalDateTime.now()
)