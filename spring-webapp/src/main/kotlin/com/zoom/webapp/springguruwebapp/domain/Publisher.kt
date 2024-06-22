package com.zoom.webapp.springguruwebapp.domain

import jakarta.persistence.*

@Entity
data class Publisher(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    var name: String,
    var address: String,
    var city: String? = null,
    var state: String? = null,
    var zipCode: String? = null,

    @OneToMany(mappedBy = "publisher")
    var books: MutableSet<Book> = mutableSetOf()
) {
    override fun toString(): String {
        return "Publisher(id=$id, name='$name', address='$address', city='$city', state='$state', zipCode='$zipCode', books=$books)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Publisher

        return id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }


}