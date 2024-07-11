package com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.mappers;

import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.entities.Beer;
import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.models.BeerDTO;
import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.models.BeerStyle;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class BeerMapperTest {
    private final BeerMapper beerMapper = Mappers.getMapper(BeerMapper.class);

    @Test
    void shouldMapBeerToBeerDTO() {
        // given
        Beer beer = new Beer();
        beer.setId(UUID.randomUUID());
        beer.setVersion(1);
        beer.setName("Test Beer");
        beer.setBeerStyle(BeerStyle.LAGER);
        beer.setUpc("123456789012");
        beer.setQuantityOnHand(10);
        beer.setPrice(BigDecimal.valueOf(9.99));
        beer.setCreateDate(LocalDateTime.now());
        beer.setUpdateDate(LocalDateTime.now());

        // when
        BeerDTO beerDTO = beerMapper.toDto(beer);

        // then
        assertThat(beerDTO).isNotNull();
        assertThat(beerDTO.getId()).isEqualTo(beer.getId());
        assertThat(beerDTO.getName()).isEqualTo(beer.getName());
        assertThat(beerDTO.getVersion()).isEqualTo(beer.getVersion());
        assertThat(beerDTO.getCreateDate()).isEqualTo(beer.getCreateDate());
        assertThat(beerDTO.getUpdateDate()).isEqualTo(beer.getUpdateDate());
    }

    @Test
    void shouldMapBeerDTOToBeer() {
        // given
        BeerDTO beerDTO = new BeerDTO();
        beerDTO.setId(UUID.randomUUID());
        beerDTO.setVersion(1);
        beerDTO.setName("Test Beer DTO");
        beerDTO.setStyle(BeerStyle.LAGER); // Assuming BeerStyle is an enum
        beerDTO.setUpc("123456789012");
        beerDTO.setQuantity(10);
        beerDTO.setPrice(BigDecimal.valueOf(9.99));
        beerDTO.setCreateDate(LocalDateTime.now());
        beerDTO.setUpdateDate(LocalDateTime.now());

        // when
        Beer beer = beerMapper.toEntity(beerDTO);

        // then
        assertThat(beer).isNotNull();
        assertThat(beer.getId()).isEqualTo(beerDTO.getId());
        assertThat(beer.getName()).isEqualTo(beerDTO.getName());
        assertThat(beer.getVersion()).isEqualTo(beerDTO.getVersion());
        assertThat(beer.getCreateDate()).isEqualTo(beerDTO.getCreateDate());
        assertThat(beer.getUpdateDate()).isEqualTo(beerDTO.getUpdateDate());
    }
}
