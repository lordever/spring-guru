package com.spring_guru.spring_DI.spring_DI.controllers

import com.spring_guru.spring_DI.spring_DI.services.GreetingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

@Controller
class PropertyInjectedController {

    @Autowired
    lateinit var greetingService: GreetingService

    fun sayHello(): String {
        return greetingService.sayGreeting()
    }
}