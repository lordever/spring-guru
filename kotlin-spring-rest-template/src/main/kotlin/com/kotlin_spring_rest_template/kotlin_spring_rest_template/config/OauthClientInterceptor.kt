package com.kotlin_spring_rest_template.kotlin_spring_rest_template.config

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.registration.ClientRegistration
import java.util.*


class OauthClientInterceptor(
    val clientRegistration: ClientRegistration,
    private val manager: OAuth2AuthorizedClientManager,
    private val authentication: Authentication
) : ClientHttpRequestInterceptor {
    override fun intercept(
        request: HttpRequest,
        body: ByteArray,
        execution: ClientHttpRequestExecution
    ): ClientHttpResponse {
        val oauth2AuthorizeRequest: OAuth2AuthorizeRequest = OAuth2AuthorizeRequest
            .withClientRegistrationId(clientRegistration.clientId)
            .principal(authentication)
            .build()

        val client: OAuth2AuthorizedClient = manager.authorize(oauth2AuthorizeRequest)
            ?: throw IllegalStateException("Missing credentials")

        request.headers.add(HttpHeaders.AUTHORIZATION, "Bearer ${client.accessToken}")

        return execution.execute(request, body)
    }

    private fun createPrincipal(): Authentication {
        return object : Authentication {
            override fun getAuthorities(): Collection<GrantedAuthority?> {
                return Collections.emptySet()
            }

            override fun getCredentials(): Any? {
                return null
            }

            override fun getDetails(): Any? {
                return null
            }

            override fun getPrincipal(): Any {
                return this
            }

            override fun isAuthenticated(): Boolean {
                return false
            }

            @Throws(IllegalArgumentException::class)
            override fun setAuthenticated(isAuthenticated: Boolean) {
            }

            override fun getName(): String {
                return clientRegistration.getClientId()
            }
        }
    }
}