package com.kotlin_spring_reactive_examples.kotlin_spring_reactive_examples.repositories

import com.kotlin_spring_reactive_examples.kotlin_spring_reactive_examples.domain.Person
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class PersonRepositoryImpl : PersonRepository {
    val michael = Person(
        id = 1,
        firstName = "Michael",
        lastName = "Jackson",
    )

    val fiona = Person(
        id = 2,
        firstName = "Fiona",
        lastName = "Shrek",
    )


    val sam = Person(
        id = 3,
        firstName = "Sam",
        lastName = "Gangy",
    )

    val jesse = Person(
        id = 4,
        firstName = "Jesse",
        lastName = "Jay",
    )

    override fun getById(id: Int): Mono<Person> {
        return Mono.just(michael)
    }

    override fun findAll(): Flux<Person> {
        return Flux.just(michael, fiona, sam, jesse)
    }
}