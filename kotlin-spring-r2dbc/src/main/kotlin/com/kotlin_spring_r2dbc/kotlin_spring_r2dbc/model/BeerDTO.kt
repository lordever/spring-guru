package com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class BeerDTO(
    val id: Int? = null,
    val name: String? = null,
    val style: String? = null,
    val upc: String? = null,
    val quantity: Int? = null,
    val price: BigDecimal? = null,
    val createdDate: LocalDateTime? = null,
    val lastModifiedDate: LocalDateTime? = null,
)