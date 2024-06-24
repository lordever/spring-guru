package com.spring_guru.spring_DI.spring_DI.services

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Profile("prod")
@Service
class ProdEnvironmentServiceImpl: EnvironmentService {
    override fun getEnv(): String {
        return "Hello from prod environment"
    }
}