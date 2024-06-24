package com.spring_guru.spring_DI.spring_DI.services

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Profile(value = ["EN","default"])
@Service("i18nService")
class EnglishGreetingServiceImpl : GreetingService {
    override fun sayGreeting(): String {
        return "Hello World - EN!"
    }
}