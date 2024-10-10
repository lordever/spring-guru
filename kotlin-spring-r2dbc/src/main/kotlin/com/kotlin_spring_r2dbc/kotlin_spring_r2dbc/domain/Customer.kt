package com.kotlin_spring_r2dbc.kotlin_spring_r2dbc.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Version
import java.time.LocalDateTime

data class Customer(
    @Id
    val id: Int? = null,
    var name: String? = null,

    @Version
    var version: Int? = null,

    @CreatedDate
    var createdDate: LocalDateTime? = null,

    @LastModifiedDate
    var lastModifiedDate: LocalDateTime? = null,
)