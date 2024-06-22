package com.zoom.webapp.springguruwebapp.services;

import com.zoom.webapp.springguruwebapp.domain.Author;

public interface AuthorService {
    Iterable<Author> findAll();
}
