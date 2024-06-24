package com.spring_guru.spring_DI.spring_DI.services

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Profile("ES")
@Service("i18nService")
class SpanishGreetingServiceImpl : GreetingService {
    override fun sayGreeting(): String {
        return "Hello World - ES!"
    }
}