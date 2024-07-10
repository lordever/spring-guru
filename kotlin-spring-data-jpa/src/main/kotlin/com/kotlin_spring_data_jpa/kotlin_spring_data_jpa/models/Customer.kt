package com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.models

import java.time.LocalDateTime
import java.util.UUID

data class Customer(
    var id: UUID?,
    var name: String?,
    var version: String?,
    val createdDate: LocalDateTime = LocalDateTime.now(),
    var lastModifiedDate: LocalDateTime = LocalDateTime.now(),
)