package com.kotlin_spring_security.kotlin_spring_security.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.kotlin_spring_security.kotlin_spring_security.config.Oauth2SecurityConfig
import com.kotlin_spring_security.kotlin_spring_security.models.CustomerDTO
import com.kotlin_spring_security.kotlin_spring_security.services.CustomerService
import com.kotlin_spring_security.kotlin_spring_security.services.CustomerServiceImpl
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.Instant
import java.util.*

@WebMvcTest(CustomerController::class)
@Import(Oauth2SecurityConfig::class)
class CustomerControllerTestKotlin {
    companion object {
        val jwtProcessor: SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor = jwt().jwt { jwt ->
            jwt.claims { claims ->
                claims["scope"] = "message-read"
                claims["scope"] = "message-write"
            }
                .subject("messaging-client")
                .notBefore(Instant.now().minusSeconds(5L))
        }
    }

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @MockkBean
    lateinit var customerService: CustomerService

    private val customerServiceImpl = CustomerServiceImpl()

    @Test
    fun getCustomersList() {
        every { customerService.findAll() } returns customerServiceImpl.findAll()

        mockMvc.perform(
            get(CustomerController.BASE_CUSTOMERS_PATH).accept(MediaType.APPLICATION_JSON)
                .with(jwtProcessor)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()", equalTo(customerServiceImpl.findAll().size)))
    }

    @Test
    fun getCustomerById() {
        val testCustomer = customerServiceImpl.findAll().first()
        val customerId = requireNotNull(testCustomer.id) { "Customer ID cannot be null" }

        every { customerService.findById(any()) } returns testCustomer

        mockMvc.perform(
            get(CustomerController.CUSTOMERS_PATH_WITH_ID, customerId)
                .with(jwtProcessor)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name", equalTo(testCustomer.name)))
            .andExpect(jsonPath("$.version", equalTo(testCustomer.version)))
    }

    @Test
    fun testGetCustomerByIdNotFound() {
        every { customerService.findById(any()) } returns null

        mockMvc.perform(
            get(CustomerController.CUSTOMERS_PATH_WITH_ID, UUID.randomUUID())
                .with(BeerControllerTestKotlin.jwtProcessor)
        )
            .andExpect(status().isNotFound());
    }

    @Test
    fun testCreateNewCustomer() {
        val testCustomer =
            customerServiceImpl.findAll().firstOrNull()
                ?: throw NoSuchElementException("No customers found")

        testCustomer.id = null
        testCustomer.version = null

        every { customerService.save(any()) } returns customerServiceImpl.findAll()[0]

        mockMvc
            .perform(
                post(CustomerController.BASE_CUSTOMERS_PATH)
                    .with(jwtProcessor)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(testCustomer))
            )
            .andExpect(status().isCreated())
            .andExpect(header().exists("Location"));
    }

    @Test
    fun testUpdateCustomer() {
        val testCustomer = customerServiceImpl.findAll().first().apply {
            name = "New Test Name"
            version = 2
        }

        every { customerService.updateById(any(), any()) } returns testCustomer

        mockMvc.perform(
            put(CustomerController.CUSTOMERS_PATH_WITH_ID, testCustomer.id)
                .with(jwtProcessor)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCustomer))
        )
            .andExpect(status().isNoContent())

        verify { customerService.updateById(any(), any()) }
    }

    @Test
    fun testDeleteCustomer() {
        val testCustomer = customerServiceImpl.findAll().first()

        val uuidSlot = slot<UUID>()
        every { customerService.deleteById(capture(uuidSlot)) } returns true

        mockMvc.perform(
            delete(CustomerController.CUSTOMERS_PATH_WITH_ID, testCustomer.id)
                .with(jwtProcessor)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isNoContent())

        verify { customerService.deleteById(any()) }

        assertThat(uuidSlot.captured).isEqualTo(testCustomer.id)
    }

    @Test
    fun testPatchCustomer() {
        val testCustomer = customerServiceImpl.findAll().first()

        val customerMap: MutableMap<String, Any> = HashMap()
        customerMap["name"] = "Test Customer Name"

        val uuidSlot = slot<UUID>()
        val customerSlot = slot<CustomerDTO>()
        every { customerService.patchById(capture(uuidSlot), capture(customerSlot)) } returns testCustomer

        mockMvc.perform(
            patch(CustomerController.CUSTOMERS_PATH_WITH_ID, testCustomer.id)
                .with(jwtProcessor)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerMap))
        )
            .andExpect(status().isNoContent())

        verify { customerService.patchById(any(), any()) }
        assertThat(uuidSlot.captured).isEqualTo(testCustomer.id)
        assertThat(customerSlot.captured.name).isEqualTo(customerMap["name"])
    }
}