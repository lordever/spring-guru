package com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.models

import java.time.LocalDateTime
import java.util.UUID

data class CustomerDTO(
    var id: UUID? = null,
    var name: String? = null,
    var version: Int? = null,
    var createdDate: LocalDateTime = LocalDateTime.now(),
    var lastModifiedDate: LocalDateTime = LocalDateTime.now(),
)