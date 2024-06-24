package com.spring_guru.spring_DI.spring_DI.controllers

import com.spring_guru.spring_DI.spring_DI.services.GreetingService
import org.springframework.stereotype.Controller

//Error here is ok, because in tests you still cn see result
@Controller
class ConstructorInjectedController(val greetingService: GreetingService) {
    fun sayHello(): String {
        return greetingService.sayGreeting()
    }
}