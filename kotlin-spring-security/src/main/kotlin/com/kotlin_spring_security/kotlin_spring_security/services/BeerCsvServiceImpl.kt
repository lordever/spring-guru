package com.kotlin_spring_security.kotlin_spring_security.services

import com.kotlin_spring_security.kotlin_spring_security.models.BeerCSVRecord
import com.opencsv.bean.CsvToBeanBuilder
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileNotFoundException
import java.io.FileReader

@Service
class BeerCsvServiceImpl : BeerCsvService {

    override fun convertCsv(file: File): List<BeerCSVRecord> {
        return try {
            val beerCSVRecords = CsvToBeanBuilder<BeerCSVRecord>(FileReader(file))
                .withType(BeerCSVRecord::class.java)
                .build()
                .parse()
            beerCSVRecords
        } catch (e: FileNotFoundException) {
            throw RuntimeException(e)
        }
    }
}