package com.spring_guru.spring_DI.spring_DI.controllers

import org.springframework.stereotype.Controller

@Controller
class MyController {
    fun sayHello(): String {
        println("I'm in controller")

        return "Hello everyone";
    }
}