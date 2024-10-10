package com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.model

import java.math.BigDecimal
import java.time.LocalDateTime
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

data class BeerDTO(
    var id: Int? = null,

    @field:NotBlank
    @field:Size(min = 3, max = 255)
    var name: String? = null,

    @field:Size(min = 3, max = 255)
    var style: String? = null,

    @field:Size(max = 25)
    var upc: String? = null,
    var quantity: Int? = null,
    var price: BigDecimal? = null,
    var createdDate: LocalDateTime? = null,
    var lastModifiedDate: LocalDateTime? = null,
)