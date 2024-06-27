package com.spring_guru.spring_rest_mvc.services;

import com.spring_guru.spring_rest_mvc.models.Beer;
import com.spring_guru.spring_rest_mvc.models.BeerStyle;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class BeerServiceImpl implements BeerService{
    @Override
    public Beer getBeerById(UUID id) {
        return Beer.builder()
                .id(id)
                .version(1)
                .name("Galaxy Cat")
                .style(BeerStyle.PALE_ALE)
                .price(new BigDecimal("12.99"))
                .quantity(122)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
    }
}
