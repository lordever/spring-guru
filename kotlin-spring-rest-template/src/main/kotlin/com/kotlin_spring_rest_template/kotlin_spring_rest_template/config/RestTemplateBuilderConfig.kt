package com.kotlin_spring_rest_template.kotlin_spring_rest_template.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.web.client.RestTemplateBuilderConfigurer
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.util.DefaultUriBuilderFactory

@Configuration
class RestTemplateBuilderConfig {

    @Value("\${rest.template.rootUrl}")
    lateinit var rootUrl: String

    @Bean
    fun restTemplateBuilder(configurer: RestTemplateBuilderConfigurer): RestTemplateBuilder {
        val builder = configurer.configure(RestTemplateBuilder())

        val uriBuilderFactory = DefaultUriBuilderFactory(
            rootUrl
        )

        return builder.uriTemplateHandler(uriBuilderFactory)
    }
}