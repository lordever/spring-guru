package com.kotlin_spring_rest_template.kotlin_spring_rest_template.client

import com.kotlin_spring_rest_template.kotlin_spring_rest_template.model.BeerDTO
import org.springframework.data.domain.Page

interface BeerClient {
    fun listBeers(): Page<BeerDTO>
}