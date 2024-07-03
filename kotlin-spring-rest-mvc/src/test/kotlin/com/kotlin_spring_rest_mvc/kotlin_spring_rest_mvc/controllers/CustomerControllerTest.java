package com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.controllers;

import com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.models.Customer;
import com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.services.CustomerService;
import com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.services.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomerService customerService;

    CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl();

    @Test
    void getAllCustomersList() throws Exception {
        given(customerService.findAll()).willReturn(customerServiceImpl.findAll());

        mockMvc
                .perform(get("/api/v1/customers").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(customerServiceImpl.findAll().size())));
    }

    @Test
    void getCustomerById() throws Exception {
        Customer testCustomer = customerServiceImpl.findAll().getFirst();

        given(customerService.findById(testCustomer.getId())).willReturn(testCustomer);

        mockMvc
                .perform(get("/api/v1/customers/"+testCustomer.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(testCustomer.getName())))
                .andExpect(jsonPath("$.version", is(testCustomer.getVersion())));
    }
}
