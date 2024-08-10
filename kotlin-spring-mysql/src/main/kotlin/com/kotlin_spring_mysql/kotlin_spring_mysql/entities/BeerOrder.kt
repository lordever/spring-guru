package com.kotlin_spring_mysql.kotlin_spring_mysql.entities

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.type.SqlTypes
import java.math.BigInteger
import java.time.LocalDateTime
import java.util.*

@Entity
data class BeerOrder(
    var customerRef: String? = null,

    @field:Id
    @field:GeneratedValue(generator = "UUID")
    @field:JdbcTypeCode(SqlTypes.CHAR)
    @field:Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    var id: UUID? = null,

    @Version
    var version: Long? = null,

    @field:CreationTimestamp
    @field:Column(updatable = false)
    var createdDate: LocalDateTime? = null,

    @field:UpdateTimestamp
    var lastModifiedDate: LocalDateTime? = null,
) {
    @field:ManyToOne
    private var customer: Customer? = null

    @field:OneToMany(mappedBy = "beerOrder")
    var beerOrderLines: Set<BeerOrderLine>? = null

    init {
        customer?.beerOrders?.add(this)
    }

    fun setCustomer(customer: Customer) {
        this.customer = customer
        customer.beerOrders?.add(this)
    }
}