package com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.model

import java.math.BigDecimal
import java.time.LocalDateTime
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

data class BeerDTO(
    val id: Int? = null,

    @field:NotBlank
    @field:Size(min = 3, max = 255)
    val name: String? = null,

    @field:Size(min = 3, max = 255)
    val style: String? = null,

    @field:Size(max = 25)
    val upc: String? = null,
    val quantity: Int? = null,
    val price: BigDecimal? = null,
    val createdDate: LocalDateTime? = null,
    val lastModifiedDate: LocalDateTime? = null,
)