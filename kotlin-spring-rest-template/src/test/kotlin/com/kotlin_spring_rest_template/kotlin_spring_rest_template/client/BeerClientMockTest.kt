package com.kotlin_spring_rest_template.kotlin_spring_rest_template.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.kotlin_spring_rest_template.kotlin_spring_rest_template.config.RestTemplateBuilderConfig
import com.kotlin_spring_rest_template.kotlin_spring_rest_template.model.BeerDTO
import com.kotlin_spring_rest_template.kotlin_spring_rest_template.model.BeerDTOPageImpl
import com.kotlin_spring_rest_template.kotlin_spring_rest_template.model.BeerStyle
import com.kotlin_spring_rest_template.kotlin_spring_rest_template.model.ListBeersFilter
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest
import org.springframework.boot.test.web.client.MockServerRestTemplateCustomizer
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Import
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers.*
import org.springframework.test.web.client.response.MockRestResponseCreators.withAccepted
import org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess
import org.springframework.web.util.UriComponentsBuilder
import java.math.BigDecimal
import java.util.UUID

@RestClientTest
@Import(RestTemplateBuilderConfig::class)
class BeerClientMockTest {
    companion object {
        const val URL = "http://localhost:8080"
    }

    private lateinit var beerClient: BeerClientImpl

    private lateinit var server: MockRestServiceServer

    @Autowired
    lateinit var restTemplateBuilderConfigured: RestTemplateBuilder

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Mock
    var mockRestTemplateBuilder: RestTemplateBuilder = RestTemplateBuilder(MockServerRestTemplateCustomizer())

    private lateinit var dtoStr: String;
    private lateinit var dtoJson: BeerDTO;

    @BeforeEach
    fun setUp() {
        val restTemplate = restTemplateBuilderConfigured.build()
        server = MockRestServiceServer.bindTo(restTemplate).build()
        Mockito.`when`(mockRestTemplateBuilder.build()).thenReturn(restTemplate)
        beerClient = BeerClientImpl(mockRestTemplateBuilder)

        dtoJson = getBeerDto()
        dtoStr = objectMapper.writeValueAsString(dtoJson)
    }

    @Test
    fun listBeers() {
        val payload = objectMapper.writeValueAsString(getPage())

        server.expect(method(HttpMethod.GET))
            .andExpect(requestTo(URL + BeerClientImpl.GET_BEER_PATH))
            .andRespond(withSuccess(payload, MediaType.APPLICATION_JSON))

        val filter = ListBeersFilter()

        val beers = beerClient.listBeers(filter)
        assertThat(beers?.size).isGreaterThan(0)
    }

    @Test
    fun getBeerById() {

        server.expect(method(HttpMethod.GET))
            .andExpect(requestToUriTemplate(URL + BeerClientImpl.GET_BEER_BY_ID_PATH, dtoJson.id))
            .andRespond(withSuccess(dtoStr, MediaType.APPLICATION_JSON))

        val beer = beerClient.getBeerById(dtoJson.id)
        assertThat(beer).isNotNull
        assertThat(beer?.id).isEqualTo(dtoJson.id)
    }

    @Test
    fun createBeer() {
        val uri = UriComponentsBuilder.fromPath(BeerClientImpl.GET_BEER_BY_ID_PATH)
            .build(dtoJson.id)

        server.expect(method(HttpMethod.POST))
            .andExpect(requestTo(URL + BeerClientImpl.GET_BEER_PATH))
            .andRespond(withAccepted().location(uri))

        server.expect(method(HttpMethod.GET))
            .andExpect(requestToUriTemplate(URL + BeerClientImpl.GET_BEER_BY_ID_PATH, dtoJson.id))
            .andRespond(withSuccess(dtoStr, MediaType.APPLICATION_JSON))

        val beer = beerClient.createBeer(dtoJson)
        assertThat(beer).isNotNull
        assertThat(beer?.id).isEqualTo(dtoJson.id)
    }

    private fun getPage(): BeerDTOPageImpl =
        BeerDTOPageImpl(listOf(getBeerDto()), 1, 25, 1)

    private fun getBeerDto(): BeerDTO =
        BeerDTO(
            id = UUID.fromString("1b3f854f-9cab-4871-9125-8e93dce3b26d"),
            price = BigDecimal("10.99"),
            name = "Mango Bobs",
            style = BeerStyle.IPA,
            quantity = 500,
            upc = "12345"
        )
}