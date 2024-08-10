package com.kotlin_spring_mysql.kotlin_spring_mysql.entities

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.type.SqlTypes
import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.*

@Entity
data class BeerOrder(
    @Id
    @GeneratedValue(generator = "UUID")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    var id: UUID? = null,

    @Version
    var version: Long? = null,

    @CreationTimestamp
    @Column(updatable = false)
    var createdDate: Timestamp? = null,

    @UpdateTimestamp
    var lastModifiedDate: Timestamp? = null,

    var customerRef: String? = null,

    @ManyToOne
    var customer: Customer? = null,

    @OneToMany(mappedBy = "beerOrder")
    var beerOrderLines: Set<BeerOrderLine>? = null,

    @OneToOne(cascade = [CascadeType.PERSIST])
    var beerOrderShipment: BeerOrderShipment? = null
) {
    init {
        customer?.beerOrders?.add(this)
        beerOrderShipment?.beerOrder = this
    }

    fun isNew(): Boolean = this.id == null

    fun assignCustomer(customer: Customer) {
        this.customer = customer
        customer.beerOrders?.add(this)
    }

    fun assignBeerOrderShipment(beerOrderShipment: BeerOrderShipment) {
        this.beerOrderShipment = beerOrderShipment
        beerOrderShipment.beerOrder = this
    }
}