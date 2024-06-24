package com.spring_guru.spring_DI.spring_DI.services

class GreetingServiceImpl: GreetingService {
    override fun sayGreeting(): String {
        return "Hello Everyone from base service"
    }
}