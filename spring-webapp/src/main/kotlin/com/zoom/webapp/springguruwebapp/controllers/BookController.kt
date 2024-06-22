package com.zoom.webapp.springguruwebapp.controllers

import com.zoom.webapp.springguruwebapp.services.AuthorService
import com.zoom.webapp.springguruwebapp.services.BookService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class BookController(
    private val bookService: BookService
) {
    @RequestMapping("/books")
    fun getAuthors(model: Model): String {
        model.addAttribute("books", bookService.findAll())

        return "books"
    }
}