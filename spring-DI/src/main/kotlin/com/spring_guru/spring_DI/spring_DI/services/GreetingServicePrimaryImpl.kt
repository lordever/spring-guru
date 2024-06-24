package com.spring_guru.spring_DI.spring_DI.services

import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Primary
@Service
class GreetingServicePrimaryImpl: GreetingService {
    override fun sayGreeting(): String {
        return "Hello from the Primary Bean!"
    }
}