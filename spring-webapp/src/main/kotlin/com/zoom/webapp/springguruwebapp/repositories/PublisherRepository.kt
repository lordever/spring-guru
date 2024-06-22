package com.zoom.webapp.springguruwebapp.repositories

import com.zoom.webapp.springguruwebapp.domain.Publisher
import org.springframework.data.repository.CrudRepository

interface PublisherRepository: CrudRepository<Publisher, Long> {
}