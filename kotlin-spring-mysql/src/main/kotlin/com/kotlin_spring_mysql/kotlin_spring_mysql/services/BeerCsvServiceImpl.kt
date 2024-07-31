package com.kotlin_spring_mysql.kotlin_spring_mysql.services

import com.kotlin_spring_mysql.kotlin_spring_mysql.models.BeerCSVRecord
import com.opencsv.bean.CsvToBeanBuilder
import java.io.File
import java.io.FileNotFoundException
import java.io.FileReader

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