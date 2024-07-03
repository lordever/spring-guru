package com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.models

import java.time.LocalDateTime
import java.util.UUID

data class Customer(
    var id: UUID?,
    var name: String?,
    var version: String?,
    val createdDate: LocalDateTime = LocalDateTime.now(),
    var lastModifiedDate: LocalDateTime = LocalDateTime.now(),
)