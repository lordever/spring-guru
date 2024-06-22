package com.zoom.webapp.springguruwebapp.services

import com.zoom.webapp.springguruwebapp.domain.Author
import com.zoom.webapp.springguruwebapp.repositories.AuthorRepository
import org.springframework.stereotype.Service

@Service
class AuthorServiceImpl(
    private val authorRepository: AuthorRepository
) : AuthorService {
    override fun findAll(): Iterable<Author> {
        return authorRepository.findAll()
    }
}