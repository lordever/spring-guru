package com.spring_guru.spring_DI.spring_DI.controllers

import com.spring_guru.spring_DI.spring_DI.services.GreetingService
import com.spring_guru.spring_DI.spring_DI.services.GreetingServiceImpl
import org.springframework.stereotype.Controller

@Controller
class MyController() {

    final lateinit var service: GreetingService

    init {
        service = GreetingServiceImpl()
    }

    fun sayHello(): String {
        println("I'm in controller")

        return service.sayGreeting()
    }
}