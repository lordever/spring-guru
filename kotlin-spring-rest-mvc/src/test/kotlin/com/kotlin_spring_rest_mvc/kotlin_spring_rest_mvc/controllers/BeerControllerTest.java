package com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.models.Beer;
import com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.services.BeerService;
import com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.services.BeerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerService beerService;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<Beer> beerCaptor;

    BeerServiceImpl beerServiceImpl;


    @BeforeEach
    void setUp() {
        beerServiceImpl = new BeerServiceImpl();
    }

    @Test
    void testGetAllBeers() throws Exception {
        given(beerService.listBeer()).willReturn(beerServiceImpl.listBeer());

        mockMvc.perform(get(BeerController.BASE_BEER_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(beerServiceImpl.listBeer().size())));
    }

    @Test
    void testGetBeerById() throws Exception {
        Beer testBeer = beerServiceImpl.listBeer().getFirst();

        given(beerService.getBeerById(testBeer.getId())).willReturn(testBeer);

        mockMvc.perform(get(BeerController.BEER_PATH_WITH_ID, testBeer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
                .andExpect(jsonPath("$.name", is(testBeer.getName())));
    }

    @Test
    void testGetBeerByIdNotFound() throws Exception {
        given(beerService.getBeerById(any(UUID.class))).willReturn(null);

        mockMvc.perform(get(BeerController.BEER_PATH_WITH_ID, UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreteNewBeer() throws Exception {
        Beer testBeer = beerServiceImpl.listBeer().getFirst();

        testBeer.setVersion(null);
        testBeer.setId(null);

        given(beerService.save(any(Beer.class))).willReturn(beerServiceImpl.listBeer().getFirst());

        mockMvc.perform(post(BeerController.BASE_BEER_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testBeer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testUpdateBeer() throws Exception {
        Beer testBeer = beerServiceImpl.listBeer().getFirst();

        mockMvc.perform(put(BeerController.BEER_PATH_WITH_ID,testBeer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testBeer)))
                .andExpect(status().isNoContent());

        verify(beerService).updateById(any(UUID.class), any(Beer.class));
    }

    @Test
    void testDeleteBeer() throws Exception {
        Beer testBeer = beerServiceImpl.listBeer().getFirst();

        assertNotNull(testBeer.getId(), "The beer ID should not be null");

        mockMvc.perform(delete(BeerController.BEER_PATH_WITH_ID, testBeer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(beerService).deleteById(uuidArgumentCaptor.capture());

        assertThat(uuidArgumentCaptor.getValue()).isEqualTo(testBeer.getId());
    }

    @Test
    void testPatchBeer() throws Exception {
        Beer testBeer = beerServiceImpl.listBeer().getFirst();

        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("name", "New Name");

        mockMvc.perform(patch(BeerController.BEER_PATH_WITH_ID, testBeer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerMap))
                )
                .andExpect(status().isNoContent());

        verify(beerService).patchById(uuidArgumentCaptor.capture(), beerCaptor.capture());
        assertThat(uuidArgumentCaptor.getValue()).isEqualTo(testBeer.getId());
        assertThat(beerCaptor.getValue().getName()).isEqualTo(beerMap.get("name"));
    }
}
