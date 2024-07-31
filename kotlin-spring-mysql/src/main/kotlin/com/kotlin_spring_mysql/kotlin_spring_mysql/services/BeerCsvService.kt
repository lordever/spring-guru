package com.kotlin_spring_mysql.kotlin_spring_mysql.services

import com.kotlin_spring_mysql.kotlin_spring_mysql.models.BeerCSVRecord
import java.io.File

interface BeerCsvService {
    fun convertCsv(file: File): List<BeerCSVRecord>
}