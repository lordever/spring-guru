package com.kotlin_spring_security.kotlin_spring_security.services

import com.kotlin_spring_security.kotlin_spring_security.models.BeerCSVRecord
import java.io.File

interface BeerCsvService {
    fun convertCsv(file: File): List<BeerCSVRecord>
}