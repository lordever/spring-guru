package com.zoom.webapp.springguruwebapp.repositories;

import com.zoom.webapp.springguruwebapp.domain.Publisher;
import org.springframework.data.repository.CrudRepository;

public interface PublisherRepository extends CrudRepository<Publisher, Long> {
}
