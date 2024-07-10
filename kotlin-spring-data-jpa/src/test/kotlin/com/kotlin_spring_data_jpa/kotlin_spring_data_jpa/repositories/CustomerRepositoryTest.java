package com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.repositories;

import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.entities.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void testSaveCustomer() {
        Customer customer = new Customer();
        customer.setName("Test Customer");

        Customer savedCustomer = customerRepository.save(customer);
        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getId()).isNotNull();
        assertThat(savedCustomer.getName()).isEqualTo(customer.getName());
    }
}
