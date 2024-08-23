package com.kotlin_spring_rest_template.kotlin_spring_rest_template.client

import com.kotlin_spring_rest_template.kotlin_spring_rest_template.model.BeerDTO
import com.kotlin_spring_rest_template.kotlin_spring_rest_template.model.BeerDTOPageImpl
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class BeerClientImpl(val restTemplateBuilder: RestTemplateBuilder) : BeerClient {
    companion object {
        const val GET_BEER_PATH = "/api/v1/beers"
    }

    override fun listBeers(): Page<BeerDTO>? {
        val restTemplate = restTemplateBuilder.build()

        val mapResponse: ResponseEntity<Map<*, *>> =
            restTemplate.getForEntity(GET_BEER_PATH, Map::class.java)


        val response: ResponseEntity<BeerDTOPageImpl> =
            restTemplate.getForEntity(GET_BEER_PATH, BeerDTOPageImpl::class.java)

        return null
    }
}