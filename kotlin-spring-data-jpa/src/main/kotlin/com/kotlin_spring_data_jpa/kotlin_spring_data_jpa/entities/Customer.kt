package com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Version
import java.time.LocalDateTime
import java.util.*

@Entity
data class Customer(
    @Id
    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length=36,columnDefinition = "varchar", updatable = false, nullable = false)
    val id: UUID? = null,
    val name: String? = null,

    @Version
    val version: Int? = null,
    val createdDate: LocalDateTime = LocalDateTime.now(),
    val lastModifiedDate: LocalDateTime = LocalDateTime.now(),
)