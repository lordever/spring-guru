package com.kotlin_spring_rest_template.kotlin_spring_rest_template.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.kotlin_spring_rest_template.kotlin_spring_rest_template.config.RestTemplateBuilderConfig
import com.kotlin_spring_rest_template.kotlin_spring_rest_template.model.BeerDTO
import com.kotlin_spring_rest_template.kotlin_spring_rest_template.model.BeerDTOPageImpl
import com.kotlin_spring_rest_template.kotlin_spring_rest_template.model.BeerStyle
import com.kotlin_spring_rest_template.kotlin_spring_rest_template.model.ListBeersFilter
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest
import org.springframework.boot.test.web.client.MockServerRestTemplateCustomizer
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Import
import org.springframework.data.domain.Page
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers.*
import org.springframework.test.web.client.response.MockRestResponseCreators.*
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.util.UriComponentsBuilder
import java.math.BigDecimal
import java.net.URI
import java.util.*

@RestClientTest
@Import(RestTemplateBuilderConfig::class)
class BeerClientMockTest {
    companion object {
        const val URL = "http://localhost:8080"
        const val TEST_BEER_NAME = "ALE"
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
        val response = objectMapper.writeValueAsString(getPage())

        server.expect(method(HttpMethod.GET))
            .andExpect(requestTo(URL + BeerClientImpl.GET_BEER_PATH))
            .andRespond(withSuccess(response, MediaType.APPLICATION_JSON))

        val filter = ListBeersFilter()

        val beers = beerClient.listBeers(filter)
        assertThat(beers?.size).isGreaterThan(0)
    }

    @Test
    fun listBeersWithQueryParams() {
        val response = objectMapper.writeValueAsString(getPage())

        val uri: URI = UriComponentsBuilder.fromHttpUrl(URL + BeerClientImpl.GET_BEER_PATH)
            .queryParam("name", TEST_BEER_NAME)
            .build()
            .toUri()

        server.expect(method(HttpMethod.GET))
            .andExpect(requestTo(uri))
            .andExpect(queryParam("name", TEST_BEER_NAME))
            .andRespond(withSuccess(response, MediaType.APPLICATION_JSON))

        val filter = ListBeersFilter(name = TEST_BEER_NAME)
        val responsePage: Page<BeerDTO>? = beerClient.listBeers(filter)
        assertThat(responsePage?.content?.size).isEqualTo(1)
    }


    @Test
    fun getBeerById() {

        mockGetOperation()

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

        mockGetOperation()

        val beer = beerClient.createBeer(dtoJson)
        assertThat(beer).isNotNull
        assertThat(beer?.id).isEqualTo(dtoJson.id)
    }

    @Test
    fun updateBeer() {
        server.expect(method(HttpMethod.PUT))
            .andExpect(requestToUriTemplate(URL + BeerClientImpl.GET_BEER_BY_ID_PATH, dtoJson.id))
            .andRespond(withNoContent())

        mockGetOperation()

        val updatedBeer = beerClient.updateBeer(dtoJson)
        assertThat(updatedBeer?.id).isEqualTo(dtoJson.id)
    }

    @Test
    fun deleteBeer() {
        server.expect(method(HttpMethod.DELETE))
            .andExpect(requestToUriTemplate(URL + BeerClientImpl.GET_BEER_BY_ID_PATH, dtoJson.id))
            .andRespond(withNoContent())

        beerClient.deleteBeer(dtoJson.id)

        server.verify()
    }

    @Test
    fun deleteNotFound() {
        server.expect(method(HttpMethod.DELETE))
            .andExpect(requestToUriTemplate(URL + BeerClientImpl.GET_BEER_BY_ID_PATH, dtoJson.id))
            .andRespond(withResourceNotFound())

        assertThrows(HttpClientErrorException::class.java) {
            beerClient.deleteBeer(dtoJson.id)
        }
    }


    private fun mockGetOperation() {
        server.expect(method(HttpMethod.GET))
            .andExpect(requestToUriTemplate(URL + BeerClientImpl.GET_BEER_BY_ID_PATH, dtoJson.id))
            .andRespond(withSuccess(dtoStr, MediaType.APPLICATION_JSON))
    }

    private fun getPage(): BeerDTOPageImpl =
        BeerDTOPageImpl(listOf(getBeerDto()), 1, 25, 1)

    private fun getBeerDto(): BeerDTO =
        BeerDTO(
            id = UUID.fromString("1b3f854f-9cab-4871-9125-8e93dce3b26d"),
            price = BigDecimal("10.99"),
            name = TEST_BEER_NAME,
            style = BeerStyle.IPA,
            quantity = 500,
            upc = "12345"
        )
}