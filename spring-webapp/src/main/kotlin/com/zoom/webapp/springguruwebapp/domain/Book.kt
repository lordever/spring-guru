package com.zoom.webapp.springguruwebapp.domain

import jakarta.persistence.*

@Entity
data class Book(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    var title: String,
    var isbn: String,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "author_book",
        joinColumns = [JoinColumn(name = "book_id")],
        inverseJoinColumns = [JoinColumn(name = "author_id")]
    )
    var authors: MutableSet<Author> = mutableSetOf(),

    @ManyToOne
    var publisher: Publisher? = null
) {
    override fun toString(): String {
        return "Book(id=$id, title='$title', isbn='$isbn')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Book

        return id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }


}