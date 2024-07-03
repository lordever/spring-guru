package com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.models

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class Beer(
    var id: UUID?,
    var version: Int?,
    var quantity: Int?,
    var name: String?,
    var upc: String?,
    var style: BeerStyle?,
    var price: BigDecimal?,
    val createDate: LocalDateTime = LocalDateTime.now(),
    var updateDate: LocalDateTime = LocalDateTime.now()
)