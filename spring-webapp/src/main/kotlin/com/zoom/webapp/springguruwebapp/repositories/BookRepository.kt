package com.zoom.webapp.springguruwebapp.repositories

import com.zoom.webapp.springguruwebapp.domain.Book
import org.springframework.data.repository.CrudRepository

interface BookRepository: CrudRepository<Book, Long> {
}