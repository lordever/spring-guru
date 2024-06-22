package com.zoom.webapp.springguruwebapp.services;

import com.zoom.webapp.springguruwebapp.domain.Author;
import com.zoom.webapp.springguruwebapp.repositories.AuthorRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Iterable<Author> findAll() {
        return this.authorRepository.findAll();
    }
}
