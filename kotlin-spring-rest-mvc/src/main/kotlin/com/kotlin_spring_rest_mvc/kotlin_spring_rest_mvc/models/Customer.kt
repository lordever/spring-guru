package com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.models

import java.time.LocalDateTime
import java.util.*

data class Customer (
    var id: UUID? = null,
    var name: String? = null,
    var version: Int? = null,
    var createdDate: LocalDateTime = LocalDateTime.now(),
    var lastModifiedDate: LocalDateTime = LocalDateTime.now(),
)