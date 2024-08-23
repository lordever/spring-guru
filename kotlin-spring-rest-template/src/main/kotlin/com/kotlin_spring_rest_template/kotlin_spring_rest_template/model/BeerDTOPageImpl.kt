package com.kotlin_spring_rest_template.kotlin_spring_rest_template.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest

@JsonIgnoreProperties(ignoreUnknown = true, value = ["pageable"])
class BeerDTOPageImpl : PageImpl<BeerDTO> {
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    constructor(
        @JsonProperty("content") content: List<BeerDTO>,
        @JsonProperty("number") page: Int,
        @JsonProperty("size") size: Int,
        @JsonProperty("totalElements") total: Long
    ) : super(content, PageRequest.of(page, size), total)

    constructor(content: MutableList<BeerDTO>) : super(
        content
    )
}