package com.zoom.webapp.springguruwebapp.services

import com.zoom.webapp.springguruwebapp.domain.Author

interface AuthorService {
    fun findAll(): Iterable<Author>
}