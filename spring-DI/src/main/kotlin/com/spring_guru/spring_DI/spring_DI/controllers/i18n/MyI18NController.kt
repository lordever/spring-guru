package com.spring_guru.spring_DI.spring_DI.controllers.i18n

import com.spring_guru.spring_DI.spring_DI.services.GreetingService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Controller

@Controller
class MyI18NController(@Qualifier("i18nService") val greetingService: GreetingService) {
    fun sayHello(): String {
        return greetingService.sayGreeting()
    }
}