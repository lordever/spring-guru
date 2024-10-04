package com.kotlin_spring_reactive_examples.kotlin_spring_reactive_examples.repositories

import com.kotlin_spring_reactive_examples.kotlin_spring_reactive_examples.domain.Person
import io.mockk.InternalPlatformDsl.toStr
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
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
    fun testGetByIdInteraction() {
        val expectedName = "Sam"
        val personMono: Mono<Person> = personRepository.getById(3)

        personMono.subscribe { person -> assertEquals(expectedName, person.firstName) }
    }

    @Test
    fun testMapOperation() {
        val personMono: Mono<Person> = personRepository.getById(1)

        personMono
            .map { person -> person.firstName }
            .subscribe { firstName -> println(firstName) }
    }

    @Test
    fun testFluxBlockFirst() {
        val personFlux: Flux<Person> = personRepository.findAll()
        val person: Person? = personFlux.blockFirst() //Not preferred

        println(person.toString())
    }

    @Test
    fun testFluxSubscriber() {
        val personFlux: Flux<Person> = personRepository.findAll()
        personFlux.subscribe { person -> println(person) }
    }

    @Test
    fun testFluxMap() {
        val personFlux: Flux<Person> = personRepository.findAll()
        personFlux
            .map { person -> person.firstName }
            .subscribe { firstName -> println(firstName) }
    }

    @Test
    fun testFluxToList() {
        val personFlux: Flux<Person> = personRepository.findAll()
        val personList: Mono<List<Person>> = personFlux.collectList()
        personList.subscribe { list ->
            run {
                list.forEach { person -> println(person.firstName) }
            }
        }
    }

    @Test
    fun testFilterOnName() {
        personRepository.findAll()
            .filter { person -> person.firstName.equals("Fiona") }
            .subscribe { person -> println(person.firstName) }
    }

    @Test
    fun testGetByName() {
        val personMono: Mono<Person> = personRepository.findAll()
            .filter { person -> person.firstName.equals("Fiona") }
            .next()

        personMono.subscribe { person -> println(person.firstName) }
    }


    @Test
    fun testFindPersonByNameNotFound() {
        val personFlux: Flux<Person> = personRepository.findAll()
        val nonExistingName = "Alex"

        val personMono: Mono<Person> = personFlux
            .filter { person -> person.firstName.equals(nonExistingName) }
            .single()
            .doOnError { throwable ->
                run {
                    println("Error occurred in the flux")
                    println(throwable.toStr())
                }
            }

        personMono.subscribe(
            { person -> println(person.firstName) },
            { throwable ->
                run {
                    println("Error occurred in the mono")
                    println(throwable.toStr())
                }
            }
        )
    }
}