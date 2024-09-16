package com.kotlin_spring_rest_template.kotlin_spring_rest_template.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.web.client.RestTemplateBuilderConfigurer
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.web.util.DefaultUriBuilderFactory

@Configuration
class RestTemplateBuilderConfig(
    val clientRegistrationRepository: ClientRegistrationRepository,
    val oauth2AuthorizedClientService: OAuth2AuthorizedClientService,
) {

    @Value("\${rest.template.rootUrl}")
    lateinit var rootUrl: String

    @Bean
    fun oauth2AuthorizedClientManager(): OAuth2AuthorizedClientManager {
        val authorizedClientProvider = OAuth2AuthorizedClientProviderBuilder.builder()
            .clientCredentials()
            .build()

        val oauth2AuthorizedClientManager = AuthorizedClientServiceOAuth2AuthorizedClientManager(
            clientRegistrationRepository,
            oauth2AuthorizedClientService
        )

        oauth2AuthorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider)

        return oauth2AuthorizedClientManager
    }

    @Bean
    fun restTemplateBuilder(configurer: RestTemplateBuilderConfigurer): RestTemplateBuilder =
        configurer
            .configure(RestTemplateBuilder())
            .uriTemplateHandler(
                DefaultUriBuilderFactory(
                    rootUrl
                )
            )
}