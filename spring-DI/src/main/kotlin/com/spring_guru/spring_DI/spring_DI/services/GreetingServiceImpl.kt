package com.spring_guru.spring_DI.spring_DI.services

import org.springframework.stereotype.Service

@Service
class GreetingServiceImpl: GreetingService {
    override fun sayGreeting(): String {
        return "Hello Everyone from base service"
    }
}