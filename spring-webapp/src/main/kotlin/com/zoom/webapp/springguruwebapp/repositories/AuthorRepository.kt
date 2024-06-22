package com.zoom.webapp.springguruwebapp.repositories

import com.zoom.webapp.springguruwebapp.domain.Author
import org.springframework.data.repository.CrudRepository

interface AuthorRepository: CrudRepository<Author, Long> {
}