package com.kotlin_spring_mysql.kotlin_spring_mysql.models

import com.opencsv.bean.CsvBindByName

data class BeerCSVRecord(
    @CsvBindByName
    val row: Int? = null,
    @CsvBindByName(column = "count.x")
    val count: Int? = null,
    @CsvBindByName
    val abv: String? = null,
    @CsvBindByName(column = "brewery_id")
    val breweryId: Int? = null,
    @CsvBindByName
    val id: Int? = null,
    @CsvBindByName
    val ibu: String? = null,
    @CsvBindByName
    val beer: String? = null,
    @CsvBindByName
    val style: String? = null,
    @CsvBindByName
    val style2: String? = null,
    @CsvBindByName(column = "count.y")
    val countY: String? = null,
    @CsvBindByName
    val city: String? = null,
    @CsvBindByName
    val state: String? = null,
    @CsvBindByName
    val label: String? = null,
    @CsvBindByName
    val ounces: Float? = null,

    )