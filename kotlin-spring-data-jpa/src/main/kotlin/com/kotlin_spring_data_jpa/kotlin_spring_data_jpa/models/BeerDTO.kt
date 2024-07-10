package com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.models

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class BeerDTO(
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