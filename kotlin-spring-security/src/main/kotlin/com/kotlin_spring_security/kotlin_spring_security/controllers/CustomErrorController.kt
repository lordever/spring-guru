package com.kotlin_spring_security.kotlin_spring_security.controllers

import jakarta.validation.ConstraintViolationException
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.BodyBuilder
import org.springframework.transaction.TransactionSystemException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class CustomErrorController {

    @ExceptionHandler
    fun handleJPAViolationsException(ex: TransactionSystemException): ResponseEntity<Any> {
        val responseEntity: BodyBuilder = ResponseEntity.badRequest()

        if (ex.cause!!.cause is ConstraintViolationException) {
            val ve: ConstraintViolationException = ex.cause!!.cause as ConstraintViolationException

            val errorList: List<Map<String, String>> =
                ve.constraintViolations.map { constraintViolation ->
                    mapOf(constraintViolation.propertyPath.toString() to constraintViolation.message!!)
                }

            return responseEntity.body(errorList)
        }

        return ResponseEntity.badRequest().body(responseEntity)
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