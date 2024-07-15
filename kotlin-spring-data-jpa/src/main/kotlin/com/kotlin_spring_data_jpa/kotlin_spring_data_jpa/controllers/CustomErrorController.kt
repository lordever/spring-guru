package com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.controllers

import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class CustomErrorController {
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleBindErrors(exception: MethodArgumentNotValidException): ResponseEntity<MutableList<FieldError>> {
        return ResponseEntity
            .badRequest()
            .body(exception.bindingResult.fieldErrors)
    }
}