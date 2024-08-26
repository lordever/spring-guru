package com.kotlin_spring_rest_template.kotlin_spring_rest_template.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kotlin_spring_rest_template.kotlin_spring_rest_template.config.RestTemplateBuilderConfig;
import com.kotlin_spring_rest_template.kotlin_spring_rest_template.model.BeerDTO;
import com.kotlin_spring_rest_template.kotlin_spring_rest_template.model.BeerDTOPageImpl;
import com.kotlin_spring_rest_template.kotlin_spring_rest_template.model.BeerStyle;
import com.kotlin_spring_rest_template.kotlin_spring_rest_template.model.ListBeersFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.web.client.MockServerRestTemplateCustomizer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest
@Import(RestTemplateBuilderConfig.class)
public class JavaBeerClientMockTest {

    static final String URL = "http://localhost:8080";

    BeerClient beerClient;

    MockRestServiceServer server;

    @Autowired
    RestTemplateBuilder restTemplateBuilderConfigured;

    @Autowired
    ObjectMapper objectMapper;

    @Mock
    RestTemplateBuilder mockRestTemplateBuilder = new RestTemplateBuilder(new MockServerRestTemplateCustomizer());

    @BeforeEach
    void setUp() {
        RestTemplate restTemplate = restTemplateBuilderConfigured.build();
        server = MockRestServiceServer.bindTo(restTemplate).build();
        when(mockRestTemplateBuilder.build()).thenReturn(restTemplate);
        beerClient = new BeerClientImpl(mockRestTemplateBuilder);
    }

    @Test
    void testListBeers() throws JsonProcessingException {
        String payload = objectMapper.writeValueAsString(getPage());

        server.expect(method(HttpMethod.GET))
                .andExpect(requestTo(URL + BeerClientImpl.GET_BEER_PATH))
                .andRespond(withSuccess(payload, MediaType.APPLICATION_JSON));

        ListBeersFilter filter = new ListBeersFilter();
        Page<BeerDTO> dtos = beerClient.listBeers(filter);
        assertThat(dtos.getContent().size()).isGreaterThan(0);
    }

    BeerDTO getBeerDto(){
        BeerDTO beerDTO = new BeerDTO();
        beerDTO.setId(UUID.randomUUID());
        beerDTO.setPrice(new BigDecimal("10.99"));
        beerDTO.setName("Mango Bobs");
        beerDTO.setStyle(BeerStyle.IPA);
        beerDTO.setQuantity(500);
        beerDTO.setUpc("123245");
        return beerDTO;
    }

    BeerDTOPageImpl getPage(){
        return new BeerDTOPageImpl(Arrays.asList(getBeerDto()), 1, 25, 1);
    }
}
