package com.kotlin_spring_mysql.kotlin_spring_mysql.models

import java.time.LocalDateTime
import java.util.UUID

data class CustomerDTO(
    var id: UUID? = null,
    var name: String? = null,
    var version: Int? = null,
    var createdDate: LocalDateTime = LocalDateTime.now(),
    var lastModifiedDate: LocalDateTime = LocalDateTime.now(),
)