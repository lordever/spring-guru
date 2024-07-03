package com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.controllers

import com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.services.CustomerService
import com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.services.CustomerServiceImpl
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(CustomerController::class)
class CustomerControllerTestKotlin {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var customerService: CustomerService

    private val customerServiceImpl = CustomerServiceImpl()

    @Test
    fun getCustomersList() {
        given(customerService.findAll()).willReturn(customerServiceImpl.findAll())

        mockMvc.perform(get("/api/v1/customers").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()", equalTo(customerServiceImpl.findAll().size)))
    }

    @Test
    fun getCustomerById() {
        val testCustomer = customerServiceImpl.findAll().first()
        val customerId = requireNotNull(testCustomer.id) { "Customer ID cannot be null" }

        given(customerService.findById(customerId)).willReturn(testCustomer)

        mockMvc.perform(get("/api/v1/customers/${testCustomer.id}").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name", equalTo(testCustomer.name)))
            .andExpect(jsonPath("$.version", equalTo(testCustomer.version)))
    }

}