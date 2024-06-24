package com.spring_guru.spring_DI.spring_DI.controllers

import com.spring_guru.spring_DI.spring_DI.services.EnvironmentService
import org.springframework.stereotype.Controller

@Controller
class EnvironmentController(val environment: EnvironmentService) {
    fun sayHello(): String {
        return environment.getEnv()
    }
}