package com.spring_guru.spring_rest_mvc.services;

import com.spring_guru.spring_rest_mvc.models.Beer;

import java.util.UUID;

public interface BeerService {
    Beer getBeerById(UUID id);
}
