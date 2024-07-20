package com.kotlin_spring_mysql.kotlin_spring_mysql.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Version
import lombok.Builder
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
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
    @Column(length=36,columnDefinition = "varchar(36)", updatable = false, nullable = false)
    @field:JdbcTypeCode(SqlTypes.CHAR)
    var id: UUID? = null,

    @Version
    var version: Int? = null,
)