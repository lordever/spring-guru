package com.kotlin_spring_mysql.kotlin_spring_mysql.mappers;


import com.kotlin_spring_mysql.kotlin_spring_mysql.entities.Customer;
import com.kotlin_spring_mysql.kotlin_spring_mysql.models.CustomerDTO;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerMapperTest {
    private final CustomerMapper mapper = Mappers.getMapper(CustomerMapper.class);

    @Test
    void shouldMapToDto() {
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName("Test Customer");
        customer.setVersion(1);

        CustomerDTO customerDTO = mapper.toDto(customer);

        assertThat(customerDTO).isNotNull();
        assertThat(customerDTO.getId()).isEqualTo(customer.getId());
        assertThat(customerDTO.getName()).isEqualTo(customer.getName());
        assertThat(customerDTO.getVersion()).isEqualTo(customer.getVersion());
    }

    @Test
    void shouldMapToCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(UUID.randomUUID());
        customerDTO.setName("Test Customer");
        customerDTO.setVersion(1);
        customerDTO.setCreatedDate(LocalDateTime.now());
        customerDTO.setLastModifiedDate(LocalDateTime.now());

        Customer customer = mapper.toCustomer(customerDTO);

        assertThat(customerDTO).isNotNull();
        assertThat(customerDTO.getId()).isEqualTo(customer.getId());
        assertThat(customerDTO.getName()).isEqualTo(customer.getName());
        assertThat(customerDTO.getVersion()).isEqualTo(customer.getVersion());
    }
}
