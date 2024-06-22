package com.zoom.webapp.springguruwebapp.repositories;

import com.zoom.webapp.springguruwebapp.domain.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {

}
