package com.kotlin_spring_reactive_examples.kotlin_spring_reactive_examples.repositories

import com.kotlin_spring_reactive_examples.kotlin_spring_reactive_examples.domain.Person
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface PersonRepository {
    fun getById(id: Int): Mono<Person>
    fun findAll(): Flux<Person>
}