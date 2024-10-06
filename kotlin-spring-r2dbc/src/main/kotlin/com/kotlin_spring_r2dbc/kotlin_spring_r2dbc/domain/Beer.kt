package com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.domain

import java.math.BigDecimal
import java.time.LocalDateTime
import org.springframework.data.annotation.Id

data class Beer(
    @Id
    val id: Int? = null,
    val name: String,
    val style: String,
    val upc: String,
    val quantity: Int,
    val price: BigDecimal,
    val createdDate: LocalDateTime? = null,
    val lastModifiedDate: LocalDateTime? = null,
)