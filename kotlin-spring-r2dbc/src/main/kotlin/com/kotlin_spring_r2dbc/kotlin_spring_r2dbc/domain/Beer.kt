package com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.domain

import org.springframework.data.annotation.CreatedDate
import java.math.BigDecimal
import java.time.LocalDateTime
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate

data class Beer(
    @Id
    val id: Int? = null,
    val name: String,
    val style: String,
    val upc: String,
    val quantity: Int,
    val price: BigDecimal,

    @CreatedDate
    val createdDate: LocalDateTime? = null,

    @LastModifiedDate
    val lastModifiedDate: LocalDateTime? = null,
)