package com.kotlin_spring_mysql.kotlin_spring_mysql.entities

import com.kotlin_spring_mysql.kotlin_spring_mysql.models.BeerStyle
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.type.SqlTypes
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Entity
data class Beer(
    var quantity: Int? = null,

    @field:Id
    @field:GeneratedValue(generator = "UUID")
    @field:JdbcTypeCode(SqlTypes.CHAR)
    @field:Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    var id: UUID? = null,

    @field:Version
    var version: Int? = null,

    @field:NotNull
    var price: BigDecimal? = null,

    @field:NotBlank
    @field:NotNull
    @field:Column(length = 50)
    @field:Size(max = 50)
    var name: String? = null,

    @field:NotNull
    var style: BeerStyle? = null,

    @field:NotBlank
    @field:NotNull
    @field:Size(max = 255)
    var upc: String? = null,

    @field:CreationTimestamp
    @field:Column(updatable = false)
    var createDate: LocalDateTime? = null,

    @field:UpdateTimestamp
    var updateDate: LocalDateTime? = null,

    @field:OneToMany(mappedBy = "beer")
    var beerOrderLines: Set<BeerOrderLine>? = null,
)