package com.kotlin_spring_rest_template.kotlin_spring_rest_template.model

data class ListBeersFilter(
    var name: String? = null,
    var style: BeerStyle? = null,
    var showInventory: Boolean? = null,
    var pageNumber: Int? = null,
    var pageSize: Int? = null
) {
//    init {
//        showInventory = showInventory ?: false
//        pageNumber = pageNumber ?: 0
//        pageSize = pageSize ?: 10
//    }
}