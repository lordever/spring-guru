package com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.repositories;

import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.entities.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BeerRepositoryTest {
    @Autowired
    BeerRepository beerRepository;

    @Test
    void testSaveBer() {
        Beer beer = new Beer();
        beer.setName("Beer Name");

        Beer savedBeer = beerRepository.save(beer);
        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
        assertThat(savedBeer.getName()).isEqualTo(beer.getName());
    }
}
