package com.kotlin_spring_rest_template.kotlin_spring_rest_template.client

import com.kotlin_spring_rest_template.kotlin_spring_rest_template.model.BeerDTO
import org.springframework.data.domain.Page

class BeerClientImpl: BeerClient {
    override fun listBeers(): Page<BeerDTO> {
        TODO("Not yet implemented")
    }
}