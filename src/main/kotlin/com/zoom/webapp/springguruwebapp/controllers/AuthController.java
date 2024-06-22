package com.zoom.webapp.springguruwebapp.controllers;

import com.zoom.webapp.springguruwebapp.services.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthController {
    private final AuthorService authorService;

    public AuthController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @RequestMapping("/authors")
    public String getAuthors(Model model){
        model.addAttribute("authors", authorService.findAll());

        return "authors";
    }
}
