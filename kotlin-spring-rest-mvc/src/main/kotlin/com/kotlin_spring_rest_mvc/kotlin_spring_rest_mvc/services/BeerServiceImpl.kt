package com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.services

import com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.models.Beer
import com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.models.BeerStyle
import org.springframework.stereotype.Service
import mu.KotlinLogging
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@Service
class BeerServiceImpl: BeerService {
    private val log = KotlinLogging.logger {}

    override fun getBeerById(id: UUID): Beer {
        log.debug { "Get Beer Id in service was called" }

        return Beer(
            id = id,
            version = 1,
            name = "Galaxy Cat",
            style = BeerStyle.PALE_ALE,
            price = BigDecimal("12.99"),
            quantity = 122,
            createDate = LocalDateTime.now(),
            updateDate = LocalDateTime.now()
        )
    }
}