package com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.models.Customer;
import com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.services.CustomerService;
import com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.services.CustomerServiceImpl;
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
    ArgumentCaptor<Customer> customerCaptor;

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
        Customer testCustomer = customerServiceImpl.findAll().getFirst();

        given(customerService.findById(testCustomer.getId())).willReturn(testCustomer);

        mockMvc
                .perform(get(CustomerController.CUSTOMERS_PATH_WITH_ID, testCustomer.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(testCustomer.getName())))
                .andExpect(jsonPath("$.version", is(testCustomer.getVersion())));
    }

    @Test
    void testGetCustomerByIdNotFound() throws Exception {
        given(customerService.findById(any(UUID.class))).willThrow(NotFoundException.class);

        mockMvc.perform(get(CustomerController.CUSTOMERS_PATH_WITH_ID, UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void createNewCustomer() throws Exception {
        Customer testCustomer = customerServiceImpl.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No customers found"));

        testCustomer.setId(null);
        testCustomer.setVersion(null);

        given(customerService.save(any(Customer.class))).willReturn(customerServiceImpl.findAll().get(0));

        mockMvc
                .perform(post(CustomerController.BASE_CUSTOMERS_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCustomer))
                )
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testUpdateCustomer() throws Exception {
        Customer testCustomer = customerServiceImpl.findAll().getFirst();
        String newTestName = "New Test Name";
        String newTestVersion = "2";
        testCustomer.setName(newTestName);
        testCustomer.setVersion(newTestVersion);

        mockMvc.perform(put(CustomerController.CUSTOMERS_PATH_WITH_ID, testCustomer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCustomer)))
                .andExpect(status().isNoContent());

        verify(customerService).updateById(any(UUID.class), any(Customer.class));
    }

    @Test
    void testDeleteCustomer() throws Exception {
        Customer testCustomer = customerServiceImpl.findAll().getFirst();

        mockMvc.perform(delete(CustomerController.CUSTOMERS_PATH_WITH_ID, testCustomer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(customerService).deleteById(uuidArgumentCaptor.capture());

        assertThat(uuidArgumentCaptor.getValue()).isEqualTo(testCustomer.getId());
    }

    @Test
    void testPatchCustomer() throws Exception {
        Customer testCustomer = customerServiceImpl.findAll().getFirst();
        Map<String, Object> customerMap = new HashMap<>();

        customerMap.put("name", "Test Customer Name");

        mockMvc.perform(patch(CustomerController.CUSTOMERS_PATH_WITH_ID, testCustomer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerMap)))
                .andExpect(status().isNoContent());

        verify(customerService).patchById(uuidArgumentCaptor.capture(), customerCaptor.capture());
        assertThat(uuidArgumentCaptor.getValue()).isEqualTo(testCustomer.getId());
        assertThat(customerCaptor.getValue().getName()).isEqualTo(customerMap.get("name"));

    }
}
