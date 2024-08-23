package com.kotlin_spring_rest_template.kotlin_spring_rest_template.client

import com.kotlin_spring_rest_template.kotlin_spring_rest_template.model.BeerDTO
import com.kotlin_spring_rest_template.kotlin_spring_rest_template.model.BeerDTOPageImpl
import com.kotlin_spring_rest_template.kotlin_spring_rest_template.model.ListBeersFilter
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI
import java.util.*

@Service
class BeerClientImpl(val restTemplateBuilder: RestTemplateBuilder) : BeerClient {
    companion object {
        const val GET_BEER_PATH = "/api/v1/beers"
        const val GET_BEER_BY_ID_PATH = "${GET_BEER_PATH}/{id}"
    }

    override fun listBeers(filter: ListBeersFilter): Page<BeerDTO>? {
        val restTemplate = restTemplateBuilder.build()

        val uriComponentsBuilder = buildListBeersUriComponents(filter)

        val response: ResponseEntity<BeerDTOPageImpl> =
            restTemplate.getForEntity(uriComponentsBuilder.toUriString(), BeerDTOPageImpl::class.java)

        return response.body
    }

    private fun buildListBeersUriComponents(filter: ListBeersFilter): UriComponentsBuilder {
        val uriComponentsBuilder = UriComponentsBuilder.fromPath(GET_BEER_PATH)

        if (filter.name != null) {
            uriComponentsBuilder.queryParam("name", filter.name)
        }
        if (filter.style != null) {
            uriComponentsBuilder.queryParam("style", filter.style)
        }
        if (filter.showInventory != null) {
            uriComponentsBuilder.queryParam("showInventory", filter.showInventory)
        }
        if (filter.pageSize != null) {
            uriComponentsBuilder.queryParam("pageSize", filter.pageSize)
        }
        if (filter.pageNumber != null) {
            uriComponentsBuilder.queryParam("pageNumber", filter.pageNumber)
        }

        return uriComponentsBuilder
    }

    override fun getBeerById(id: UUID?): BeerDTO? {
        val restTemplate = restTemplateBuilder.build()

        return restTemplate.getForObject(GET_BEER_BY_ID_PATH, BeerDTO::class.java, id)
    }

    override fun createBeer(newBeerDTO: BeerDTO): BeerDTO? {
        val restTemplate = restTemplateBuilder.build()

        val uri: URI = restTemplate.postForLocation(GET_BEER_PATH, newBeerDTO) ?: return null

        return restTemplate.getForObject(uri.path, BeerDTO::class.java)
    }

    override fun updateBeer(newBeerDTO: BeerDTO): BeerDTO? {
        val restTemplate = restTemplateBuilder.build()
        restTemplate.put(GET_BEER_BY_ID_PATH, newBeerDTO, newBeerDTO.id)

        return getBeerById(newBeerDTO.id)
    }
}