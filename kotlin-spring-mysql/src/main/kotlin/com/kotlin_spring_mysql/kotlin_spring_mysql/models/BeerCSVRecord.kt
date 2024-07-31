package com.kotlin_spring_mysql.kotlin_spring_mysql.models

import com.opencsv.bean.CsvBindByName

data class BeerCSVRecord(
    @CsvBindByName
    val row: Int,
    @CsvBindByName(column = "count.x")
    val count: Int,
    @CsvBindByName
    val abv: String,
    @CsvBindByName(column = "brewery_id")
    val breweryId: Int,
    @CsvBindByName
    val id: Int,
    @CsvBindByName
    val ibu: String,
    @CsvBindByName
    val beer: String,
    @CsvBindByName
    val style: String,
    @CsvBindByName
    val style2: String,
    @CsvBindByName(column = "count.y")
    val countY: String,
    @CsvBindByName
    val city: String,
    @CsvBindByName
    val state: String,
    @CsvBindByName
    val label: String,
    @CsvBindByName
    val ounces: Float,

)