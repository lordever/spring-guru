package com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.models

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class BeerDTO(
    var id: UUID? = null,
    var version: Int? = null,
    var quantity: Int? = null,

    @field:NotBlank
    @field:NotNull
    var name: String? = null,

    @field:NotBlank
    @field:NotNull
    var upc: String? = null,

    @field:NotNull
    var style: BeerStyle? = null,

    @field:NotNull
    var price: BigDecimal? = null,
    var createDate: LocalDateTime = LocalDateTime.now(),
    var updateDate: LocalDateTime = LocalDateTime.now()
)