package com.kotlin_spring_security.kotlin_spring_security.entities

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.type.SqlTypes
import java.time.LocalDateTime
import java.util.*

@Entity
data class Category(
    var description: String? = null,

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
    var lastModifiedDate: LocalDateTime? = null
) {

    @field:ManyToMany
    @field:JoinTable(
        name = "beer_category",
        joinColumns = [JoinColumn(name = "category_id")],
        inverseJoinColumns = [JoinColumn(name = "beer_id")]
    )
    val beers: MutableSet<Beer> = mutableSetOf()
}