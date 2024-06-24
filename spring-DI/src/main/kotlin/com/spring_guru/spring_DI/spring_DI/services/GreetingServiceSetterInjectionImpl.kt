package com.spring_guru.spring_DI.spring_DI.services

import org.springframework.stereotype.Service

@Service("setterGreetingBean")
class GreetingServiceSetterInjectionImpl : GreetingService {
    override fun sayGreeting(): String {
        return "Hey I'm setting a greeting"
    }
}