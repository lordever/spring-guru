package com.kotlin_spring_rest_template.kotlin_spring_rest_template.client

import com.kotlin_spring_rest_template.kotlin_spring_rest_template.model.BeerDTO
import com.kotlin_spring_rest_template.kotlin_spring_rest_template.model.BeerDTOPageImpl
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.util.UriComponentsBuilder

@Service
class BeerClientImpl(val restTemplateBuilder: RestTemplateBuilder) : BeerClient {
    companion object {
        const val GET_BEER_PATH = "/api/v1/beers"
    }

    override fun listBeers(name: String?): Page<BeerDTO>? {
        val restTemplate = restTemplateBuilder.build()

        val uriComponentsBuilder = UriComponentsBuilder.fromPath(GET_BEER_PATH)

        if (name != null) {
            uriComponentsBuilder.queryParam("name", name)
        }

        val response: ResponseEntity<BeerDTOPageImpl> =
            restTemplate.getForEntity(uriComponentsBuilder.toUriString(), BeerDTOPageImpl::class.java)

        return response.body
    }
}