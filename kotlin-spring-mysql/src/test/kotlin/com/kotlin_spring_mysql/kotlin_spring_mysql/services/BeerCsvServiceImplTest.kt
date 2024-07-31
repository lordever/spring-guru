package com.kotlin_spring_mysql.kotlin_spring_mysql.services

import com.kotlin_spring_mysql.kotlin_spring_mysql.models.BeerCSVRecord
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.util.ResourceUtils
import java.io.File

class BeerCsvServiceImplTest {
    private val beerCsvService = BeerCsvServiceImpl()

    @Test
    fun convertCSV() {
        val file: File = ResourceUtils.getFile("classpath:csvdata/beers.csv")
        val recs: List<BeerCSVRecord> = beerCsvService.convertCsv(file)

        println(recs.size)

        assertThat(recs.size).isGreaterThan(0)
    }
}