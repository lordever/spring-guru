package com.kotlin_spring_reactive_examples.kotlin_spring_reactive_examples.repositories

import com.kotlin_spring_reactive_examples.kotlin_spring_reactive_examples.domain.Person
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono

class PersonRepositoryImplTest {

    private val personRepository: PersonRepository = PersonRepositoryImpl()

    @Test
    fun testMonoByIdBock() {
        val personMono: Mono<Person> = personRepository.getById(1)

        val person = personMono.block() //Not preferred

        println(person.toString())
    }

    @Test
    fun testGetByIdSubscriber() {
        val personMono: Mono<Person> = personRepository.getById(1)

        personMono.subscribe { person -> println(person.toString()) }
    }

    @Test
    fun testMapOperation() {
        val personMono: Mono<Person> = personRepository.getById(1)

        personMono
            .map { person -> person.firstName }
            .subscribe { firstName -> println(firstName) }
    }
}