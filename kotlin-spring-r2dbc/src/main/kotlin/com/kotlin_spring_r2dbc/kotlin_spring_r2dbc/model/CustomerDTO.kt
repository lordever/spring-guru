package com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

data class CustomerDTO(
    var id: Int? = null,

    @field:NotBlank
    @field:Size(min = 3, max = 255)
    var name: String? = null,
    var version: Int? = null,
    var createdDate: LocalDateTime? = null,
    var lastModifiedDate: LocalDateTime? = null,
)