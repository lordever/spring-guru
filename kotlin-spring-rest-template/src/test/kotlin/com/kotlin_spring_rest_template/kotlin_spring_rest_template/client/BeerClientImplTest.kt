package com.kotlin_spring_rest_template.kotlin_spring_rest_template.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.kotlin_spring_rest_template.kotlin_spring_rest_template.model.BeerDTO
import com.kotlin_spring_rest_template.kotlin_spring_rest_template.model.BeerDTOPageImpl
import com.kotlin_spring_rest_template.kotlin_spring_rest_template.model.BeerStyle
import com.kotlin_spring_rest_template.kotlin_spring_rest_template.model.ListBeersFilter
import jakarta.transaction.Transactional
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers.method
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess
import org.springframework.web.client.HttpClientErrorException
import java.math.BigDecimal
import java.util.*

@RestClientTest(BeerClientImpl::class)
class BeerClientImplTest {

    companion object {
        const val URL = "http://locahost:8080"
    }

    @Autowired
    lateinit var beerClient: BeerClientImpl

    @Autowired
    lateinit var server: MockRestServiceServer

    @Autowired
    lateinit var objectMapper: ObjectMapper

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
    fun listBeersByName() {
        val name = "ALE"

        val filter = ListBeersFilter(name = name)

        val result = beerClient.listBeers(filter)

        assertThat(result).isNotNull
        assertThat(result).isNotEmpty

        result?.content?.forEach { beer -> assertThat(beer.name).containsIgnoringCase(name) }
    }

    @Test
    fun listBeersByStyle() {
        val style = BeerStyle.IPA

        val filter = ListBeersFilter(style = style)

        val result = beerClient.listBeers(filter)

        assertThat(result).isNotNull
        assertThat(result).isNotEmpty

        result?.content?.forEach { beer -> assertThat(beer.style).isEqualTo(style) }
    }

    @Test
    fun listBeersByInventory() {

        val filter = ListBeersFilter(showInventory = true)

        val result = beerClient.listBeers(filter)

        assertThat(result).isNotNull
        assertThat(result).isNotEmpty

        result?.content?.forEach { beer -> assertThat(beer.quantity).isNull() }
    }

    @Test
    fun listBeersWithPage() {
        val pageNumber = 2
        val filter = ListBeersFilter(pageNumber = pageNumber)

        val result = beerClient.listBeers(filter)

        assertThat(result).isNotNull
        assertThat(result).isNotEmpty

        assertThat(result?.pageable?.pageNumber).isEqualTo(pageNumber - 1)
    }

    @Test
    fun listBeersWithPageSize() {
        val pageSize = 5

        val filter = ListBeersFilter(pageSize = pageSize)

        val result = beerClient.listBeers(filter)

        assertThat(result).isNotNull
        assertThat(result).isNotEmpty
        assertThat(result?.content?.size).isEqualTo(pageSize)
    }

    @Test
    fun listBeersWithPageAndPageSize() {
        val pageNumber = 2
        val pageSize = 5
        val filter = ListBeersFilter(pageNumber = pageNumber, pageSize = pageSize)

        val result = beerClient.listBeers(filter)

        assertThat(result).isNotNull
        assertThat(result).isNotEmpty

        assertThat(result?.pageable?.pageNumber).isEqualTo(pageNumber - 1)
        assertThat(result?.content?.size).isEqualTo(pageSize)
    }

    @Test
    fun getBeerById() {
        val filter = ListBeersFilter()
        val listBeerDto = beerClient.listBeers(filter)

        assertThat(listBeerDto).isNotNull
        assertThat(listBeerDto).isNotEmpty

        val beerDto = listBeerDto?.first()

        assertNotNull(beerDto)

        val beerDtoById = beerClient.getBeerById(beerDto?.id)

        assertNotNull(beerDtoById)
        assertThat(beerDtoById?.id).isEqualTo(beerDto?.id)
    }

    @Test
    fun createBeer() {
        val newBeerDTO = BeerDTO(
            price = BigDecimal("10.99"),
            name = "Mango Bobs",
            style = BeerStyle.IPA,
            quantity = 500,
            upc = "12345"
        )

        val savedBeer = beerClient.createBeer(newBeerDTO)
        assertNotNull(savedBeer)
    }

    @Test
    fun updateBeer() {
        val newBeerDTO = BeerDTO(
            price = BigDecimal("10.99"),
            name = "Mango Bobs 2",
            style = BeerStyle.IPA,
            quantity = 500,
            upc = "12345"
        )

        val savedBeer = beerClient.createBeer(newBeerDTO)
        assertNotNull(savedBeer)

        val newName = "Mango Bobs 3"
        savedBeer?.name = newName

        val updatedBeer = beerClient.updateBeer(savedBeer!!)

        assertEquals(newName, updatedBeer?.name)
    }

    @Test
    fun deleteBeer() {
        val newBeerDTO = BeerDTO(
            price = BigDecimal("10.99"),
            name = "Mango Bobs",
            style = BeerStyle.IPA,
            quantity = 500,
            upc = "12345"
        )

        val savedBeer = beerClient.createBeer(newBeerDTO)
        assertNotNull(savedBeer)

        beerClient.deleteBeer(savedBeer?.id)

        assertThrows(HttpClientErrorException::class.java) {
            beerClient.getBeerById(savedBeer?.id)
        }
    }

    private fun getPage(): BeerDTOPageImpl =
        BeerDTOPageImpl(listOf(getBeerDto()), 1, 25, 1)

    private fun getBeerDto(): BeerDTO =
        BeerDTO(
            price = BigDecimal("10.99"),
            name = "Mango Bobs",
            style = BeerStyle.IPA,
            quantity = 500,
            upc = "12345"
        )
}