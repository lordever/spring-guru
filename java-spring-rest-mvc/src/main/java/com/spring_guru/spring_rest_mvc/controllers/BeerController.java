package com.spring_guru.spring_rest_mvc.controllers;

import com.spring_guru.spring_rest_mvc.models.Beer;
import com.spring_guru.spring_rest_mvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Controller
public class BeerController {
    private final BeerService beerService;

    public Beer gerBeerById(UUID id) {
        log.debug("Get beer by id {}", id);

        return beerService.getBeerById(id);
    }
}
