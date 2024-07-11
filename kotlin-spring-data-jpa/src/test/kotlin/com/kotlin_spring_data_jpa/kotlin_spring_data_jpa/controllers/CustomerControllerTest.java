package com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.models.CustomerDTO;
import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.services.CustomerService;
import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.services.CustomerServiceImpl;
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
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CustomerService customerService;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<CustomerDTO> customerCaptor;

    CustomerServiceImpl customerServiceImpl;

    @BeforeEach
    void setUp() {
        customerServiceImpl = new CustomerServiceImpl();
    }

    @Test
    void getAllCustomersList() throws Exception {
        given(customerService.findAll()).willReturn(customerServiceImpl.findAll());

        mockMvc
                .perform(get(CustomerController.BASE_CUSTOMERS_PATH).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(customerServiceImpl.findAll().size())));
    }

    @Test
    void getCustomerById() throws Exception {
        CustomerDTO testCustomerDTO = customerServiceImpl.findAll().getFirst();

        given(customerService.findById(testCustomerDTO.getId())).willReturn(testCustomerDTO);

        mockMvc
                .perform(get(CustomerController.CUSTOMERS_PATH_WITH_ID, testCustomerDTO.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(testCustomerDTO.getName())))
                .andExpect(jsonPath("$.version", is(testCustomerDTO.getVersion())));
    }

    @Test
    void testGetCustomerByIdNotFound() throws Exception {
        given(customerService.findById(any(UUID.class))).willReturn(null);

        mockMvc.perform(get(CustomerController.CUSTOMERS_PATH_WITH_ID, UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void createNewCustomer() throws Exception {
        CustomerDTO testCustomerDTO = customerServiceImpl.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No customers found"));

        testCustomerDTO.setId(null);
        testCustomerDTO.setVersion(null);

        given(customerService.save(any(CustomerDTO.class))).willReturn(customerServiceImpl.findAll().get(0));

        mockMvc
                .perform(post(CustomerController.BASE_CUSTOMERS_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCustomerDTO))
                )
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testDeleteCustomer() throws Exception {
        CustomerDTO testCustomerDTO = customerServiceImpl.findAll().getFirst();

        mockMvc.perform(delete(CustomerController.CUSTOMERS_PATH_WITH_ID, testCustomerDTO.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(customerService).deleteById(uuidArgumentCaptor.capture());

        assertThat(uuidArgumentCaptor.getValue()).isEqualTo(testCustomerDTO.getId());
    }

    @Test
    void testUpdateCustomer() throws Exception {
        CustomerDTO testCustomerDTO = customerServiceImpl.findAll().getFirst();
        String newTestName = "New Test Name";
        Integer newTestVersion = 2;
        testCustomerDTO.setName(newTestName);
        testCustomerDTO.setVersion(newTestVersion);

        mockMvc.perform(put(CustomerController.CUSTOMERS_PATH_WITH_ID, testCustomerDTO.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCustomerDTO)))
                .andExpect(status().isNoContent());

        verify(customerService).updateById(any(UUID.class), any(CustomerDTO.class));
    }

    @Test
    void testPatchCustomer() throws Exception {
        CustomerDTO testCustomerDTO = customerServiceImpl.findAll().getFirst();
        Map<String, Object> customerMap = new HashMap<>();

        customerMap.put("name", "Test Customer Name");

        mockMvc.perform(patch(CustomerController.CUSTOMERS_PATH_WITH_ID, testCustomerDTO.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerMap)))
                .andExpect(status().isNoContent());

        verify(customerService).patchById(uuidArgumentCaptor.capture(), customerCaptor.capture());
        assertThat(uuidArgumentCaptor.getValue()).isEqualTo(testCustomerDTO.getId());
        assertThat(customerCaptor.getValue().getName()).isEqualTo(customerMap.get("name"));

    }
}
