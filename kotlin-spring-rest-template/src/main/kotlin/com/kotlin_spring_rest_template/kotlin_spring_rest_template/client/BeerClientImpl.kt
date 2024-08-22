package com.kotlin_spring_rest_template.kotlin_spring_rest_template.client

import com.kotlin_spring_rest_template.kotlin_spring_rest_template.model.BeerDTO
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class BeerClientImpl : BeerClient {

    private val restTemplateBuilder: RestTemplateBuilder = RestTemplateBuilder()

    companion object {
        const val BASE_PATH = "http://localhost:8080"
        const val GET_BEER_PATH = "api/v1/beer"
    }

    override fun listBeers(): Page<BeerDTO>? {
        val restTemplate = restTemplateBuilder.build()

        val response: ResponseEntity<String> =
            restTemplate.getForEntity("${BASE_PATH}/${GET_BEER_PATH}", String::class.java)

        val mapResponse: ResponseEntity<Map<*, *>> =
            restTemplate.getForEntity("${BASE_PATH}/${GET_BEER_PATH}", Map::class.java)


        println(response.body)
        println(mapResponse.body)

        return null
    }
}