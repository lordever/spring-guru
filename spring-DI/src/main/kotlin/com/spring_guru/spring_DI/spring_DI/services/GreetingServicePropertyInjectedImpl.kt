package com.spring_guru.spring_DI.spring_DI.services

import org.springframework.stereotype.Service

@Service("propertyGreetingService")
class GreetingServicePropertyInjectedImpl : GreetingService {
    override fun sayGreeting(): String {
        return "Friends don't let friends to property injection!"
    }
}