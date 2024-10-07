package com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.domain

import org.springframework.data.annotation.CreatedDate
import java.math.BigDecimal
import java.time.LocalDateTime
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate

data class Beer(
    @Id
    val id: Int? = null,
    var name: String? = null,
    var style: String? = null,
    var upc: String? = null,
    var quantity: Int? = null,
    var price: BigDecimal? = null,

    @CreatedDate
    var createdDate: LocalDateTime? = null,

    @LastModifiedDate
    var lastModifiedDate: LocalDateTime? = null,
)