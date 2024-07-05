package com.kotlin_spring_rest_mvc.kotlin_spring_rest_mvc.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionController {
    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(): ResponseEntity<String> {
        println("Calling from ExceptionController")

        return ResponseEntity.notFound().build()
    }
}