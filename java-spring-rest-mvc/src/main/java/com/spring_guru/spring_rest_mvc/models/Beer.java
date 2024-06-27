package com.spring_guru.spring_rest_mvc.models;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Beer {
    private UUID id;
    private Integer version;
    private Integer quantity;
    private String name;
    private String upc;
    private BeerStyle style;
    private BigDecimal price;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
