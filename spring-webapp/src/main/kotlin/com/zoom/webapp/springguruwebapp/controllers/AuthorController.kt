package com.zoom.webapp.springguruwebapp.controllers

import com.zoom.webapp.springguruwebapp.services.AuthorService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class AuthorController(
    private val authorService: AuthorService
) {
    @RequestMapping("/authors")
    fun getAuthors(model: Model): String {
        model.addAttribute("authors", authorService.findAll())

        return "authors"
    }
}