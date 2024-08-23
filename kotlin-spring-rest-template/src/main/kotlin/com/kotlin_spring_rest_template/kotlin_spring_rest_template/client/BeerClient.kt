package com.kotlin_spring_rest_template.kotlin_spring_rest_template.client

import com.kotlin_spring_rest_template.kotlin_spring_rest_template.model.BeerDTO
import com.kotlin_spring_rest_template.kotlin_spring_rest_template.model.ListBeersFilter
import org.springframework.data.domain.Page

interface BeerClient {
    fun listBeers(
        filter: ListBeersFilter
    ): Page<BeerDTO>?
}