package com.kotlin_spring_mysql.kotlin_spring_mysql.entities

import jakarta.persistence.*
import lombok.Builder
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.type.SqlTypes
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.HashSet

@Entity
@Builder
data class Customer(
    var name: String? = null,

    @field:Id
    @field:GeneratedValue(generator = "UUID")
    @field:JdbcTypeCode(SqlTypes.CHAR)
    @field:Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    var id: UUID? = null,

    @Version
    var version: Int? = null,

    @field:CreationTimestamp
    @field:Column(updatable = false)
    var createdDate: LocalDateTime? = null,

    @field:UpdateTimestamp
    var lastModifiedDate: LocalDateTime? = null,
) {
    @OneToMany(mappedBy = "customer")
    var beerOrders: MutableSet<BeerOrder>? = mutableSetOf()
}