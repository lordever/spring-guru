package com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.models

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class Beer(
    val id: UUID,
    val version: Int,
    val quantity: Int,
    val name: String,
    val upc: String = "",
    val style: BeerStyle,
    val price: BigDecimal,
    val createDate: LocalDateTime,
    val updateDate: LocalDateTime
) {
}