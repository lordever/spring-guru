package com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.models

import java.time.LocalDateTime
import java.util.UUID

data class Customer(
    val id: UUID,
    val name: String,
    val version: String,
    val createdDate: LocalDateTime,
    val lastModifiedDate: LocalDateTime
)