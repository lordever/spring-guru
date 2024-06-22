package com.zoom.webapp.springguruwebapp.domain

import jakarta.persistence.*

@Entity
data class Author(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    var firstName: String,
    var lastName: String,

    @ManyToMany(mappedBy = "authors", fetch = FetchType.EAGER)
    var books: MutableSet<Book> = mutableSetOf()
) {
    override fun toString(): String {
        return "Author(id=$id, firstName='$firstName', lastName='$lastName')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Author

        return id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }


}