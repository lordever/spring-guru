package com.kotlin_spring_rest_template.kotlin_spring_rest_template.client

import com.kotlin_spring_rest_template.kotlin_spring_rest_template.model.BeerDTO
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class BeerClientImpl : BeerClient {

    private val restTemplateBuilder: RestTemplateBuilder = RestTemplateBuilder()

    override fun listBeers(): Page<BeerDTO>? {
        val restTemplate = restTemplateBuilder.build()

        val response: ResponseEntity<String> =
            restTemplate.getForEntity("http://localhost:8080/api/v1/beer", String::class.java)

        println(response.body)

        return null
    }
}