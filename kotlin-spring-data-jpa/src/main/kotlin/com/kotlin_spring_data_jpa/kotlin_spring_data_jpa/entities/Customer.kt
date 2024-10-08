package com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.entities

import jakarta.persistence.*
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

    @field:Id
    @field:GeneratedValue(generator = "UUID")
    @field:JdbcTypeCode(SqlTypes.CHAR)
    @field:Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    var id: UUID? = null,

    @Version
    var version: Int? = null,

    @OneToMany(mappedBy = "customer")
    var beerOrders: Set<BeerOrder>
)