package com.zoom.webapp.springguruwebapp.services

import com.zoom.webapp.springguruwebapp.domain.Book

interface BookService {
    fun findAll(): Iterable<Book>
}