package com.zoom.webapp.springguruwebapp.services;

import com.zoom.webapp.springguruwebapp.domain.Book;

public interface BookService {
    Iterable<Book> findAll();
}
