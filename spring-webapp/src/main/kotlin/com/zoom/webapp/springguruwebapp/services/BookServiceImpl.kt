package com.zoom.webapp.springguruwebapp.services

import com.zoom.webapp.springguruwebapp.domain.Book
import com.zoom.webapp.springguruwebapp.repositories.BookRepository
import org.springframework.stereotype.Service

@Service
class BookServiceImpl(
    private val bookRepository: BookRepository
) : BookService {
    override fun findAll(): Iterable<Book> {
        return bookRepository.findAll()
    }
}