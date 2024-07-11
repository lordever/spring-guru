package com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Version
import lombok.Builder
import java.time.LocalDateTime
import java.util.*

@Entity
@Builder
data class Customer(
    var name: String? = null,
    var createdDate: LocalDateTime = LocalDateTime.now(),
    var lastModifiedDate: LocalDateTime = LocalDateTime.now(),

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(length=36,columnDefinition = "varchar", updatable = false, nullable = false)
    var id: UUID? = null,
    @Version
    var version: Int? = null,
)