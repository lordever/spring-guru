package com.spring_guru.spring_rest_mvc.controllers;

import com.spring_guru.spring_rest_mvc.services.BeerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

@AllArgsConstructor
@Controller
public class BeerController {
    private final BeerService beerService;
}
