package com.kotlin_spring_rest_template.kotlin_spring_rest_template.config

import org.springframework.boot.autoconfigure.web.client.RestTemplateBuilderConfigurer
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.util.DefaultUriBuilderFactory

@Configuration
class RestTemplateBuilderConfig {

    companion object {
        const val BASE_PATH = "http://localhost:8080"
    }

    @Bean
    fun restTemplateBuilder(configurer: RestTemplateBuilderConfigurer): RestTemplateBuilder {
        val builder = configurer.configure(RestTemplateBuilder())

        val uriBuilderFactory = DefaultUriBuilderFactory(
            BASE_PATH
        )

        return builder.uriTemplateHandler(uriBuilderFactory)
    }
}