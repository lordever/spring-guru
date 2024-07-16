package com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.controllers

import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.BodyBuilder
import org.springframework.transaction.TransactionSystemException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class CustomErrorController {

    @ExceptionHandler
    fun handleJPAViolationsException(ex: TransactionSystemException): ResponseEntity<BodyBuilder> {
        return ResponseEntity.badRequest().build()
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleBindErrors(exception: MethodArgumentNotValidException): ResponseEntity<List<Map<String, String?>>> {
        val errorList: List<Map<String, String?>> =
            exception.fieldErrors.map { fieldError -> mapOf(fieldError.field to fieldError.defaultMessage) }

        return ResponseEntity
            .badRequest()
            .body(errorList)
    }
}