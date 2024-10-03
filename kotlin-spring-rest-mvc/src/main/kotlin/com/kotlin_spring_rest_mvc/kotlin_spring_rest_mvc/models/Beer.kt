package com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.models

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class Beer(
    var id: UUID? = null,
    var version: Int? = null,
    var quantity: Int? = null,
    var name: String? = null,
    var upc: String? = null,
    var style: BeerStyle? = null,
    var price: BigDecimal? = null,
    var createDate: LocalDateTime = LocalDateTime.now(),
    var updateDate: LocalDateTime = LocalDateTime.now()
)