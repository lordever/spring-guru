package com.spring_guru.spring_DI.spring_DI.controllers

import com.spring_guru.spring_DI.spring_DI.services.GreetingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Controller

@Controller
class SetterInjectedController {
    lateinit var greetingService: GreetingService

    @Autowired
    @Qualifier("setterGreetingBean")
    fun setService(service: GreetingService){
        println("Hello from setter")
        this.greetingService = service
    }

    fun sayHello(): String {
        return greetingService.sayGreeting()
    }
}