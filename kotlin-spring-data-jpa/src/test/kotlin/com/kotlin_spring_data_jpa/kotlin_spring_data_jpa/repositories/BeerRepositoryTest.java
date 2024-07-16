package com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.repositories;

import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.entities.Beer;
import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.models.BeerStyle;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class BeerRepositoryTest {
    @Autowired
    BeerRepository beerRepository;

    @Test
    void testSaveBer() {
        Beer beer = new Beer();
        beer.setName("Beer Name");
        beer.setStyle(BeerStyle.ALE);
        beer.setUpc("Test UPC");
        beer.setPrice(new BigDecimal(123));

        Beer savedBeer = beerRepository.save(beer);
        beerRepository.flush();

        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
        assertThat(savedBeer.getName()).isEqualTo(beer.getName());
    }

    @Test
    void testSaveBerWhereNameIsTooLong() {
        assertThrows(ConstraintViolationException.class, () -> {
            Beer beer = new Beer();
            beer.setName("Test Beer Test Beer Test Beer Test Beer Test Beer Test Beer");
            beer.setStyle(BeerStyle.ALE);
            beer.setUpc("Test UPC");
            beer.setPrice(new BigDecimal(123));

            Beer savedBeer = beerRepository.save(beer);
            beerRepository.flush();

            assertThat(savedBeer).isNotNull();
            assertThat(savedBeer.getId()).isNotNull();
            assertThat(savedBeer.getName()).isEqualTo(beer.getName());
        });
    }
}
