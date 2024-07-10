package com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.entities

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Version
import java.time.LocalDateTime
import java.util.*

@Entity
data class CustomerDTO(

    @Id
    val id: UUID? = null,
    val name: String? = null,

    @Version
    val version: String? = null,
    val createdDate: LocalDateTime = LocalDateTime.now(),
    val lastModifiedDate: LocalDateTime = LocalDateTime.now(),
)