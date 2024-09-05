package com.kotlin_spring_security.kotlin_spring_security.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SpringSecurityConfig {
    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { configurer: CsrfConfigurer<HttpSecurity?> ->
                configurer
                    .ignoringRequestMatchers("/api/**")
            }


        return http.build()
    }
}