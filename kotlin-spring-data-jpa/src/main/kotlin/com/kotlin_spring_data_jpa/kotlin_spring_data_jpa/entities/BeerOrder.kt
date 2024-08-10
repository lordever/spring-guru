package com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.entities

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.type.SqlTypes
import java.time.LocalDateTime
import java.util.*

@Entity
data class BeerOrder(
    var customerRef: String? = null,
    var customerId: UUID? = null,

    @field:Id
    @field:GeneratedValue(generator = "UUID")
    @field:JdbcTypeCode(SqlTypes.CHAR)
    @field:Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    var id: UUID? = null,

    @Version
    var version: Int? = null,

    @field:CreationTimestamp
    @field:Column(updatable = false)
    var createDate: LocalDateTime? = null,

    @field:UpdateTimestamp
    var lastModifiedDate: LocalDateTime? = null,

    @field:ManyToOne(fetch = FetchType.LAZY)
    var customer: Customer? = null,

    @field:OneToMany(mappedBy = "beerOrder")
    var beerOrderLines: Set<BeerOrderLine>
)