package com.spring_guru.spring_DI.spring_DI.controllers

import com.spring_guru.spring_DI.spring_DI.services.GreetingService
import org.springframework.stereotype.Controller

@Controller
class SetterInjectedController {
    lateinit var greetingService: GreetingService

    fun setService(service: GreetingService){
        this.greetingService = service
    }

    fun sayHello(): String {
        return greetingService.sayGreeting()
    }
}