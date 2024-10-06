package com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class BeerDTO(
    val id: Int? = null,
    val name: String,
    val style: String,
    val upc: String,
    val quantity: Int,
    val price: BigDecimal,
    val createdDate: LocalDateTime? = null,
    val lastModifiedDate: LocalDateTime? = null,
)