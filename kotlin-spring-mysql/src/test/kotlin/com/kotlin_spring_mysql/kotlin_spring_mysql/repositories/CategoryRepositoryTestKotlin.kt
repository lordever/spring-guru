package com.kotlin_spring_mysql.kotlin_spring_mysql.repositories

import com.kotlin_spring_mysql.kotlin_spring_mysql.entities.Beer
import com.kotlin_spring_mysql.kotlin_spring_mysql.entities.Category
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class CategoryRepositoryTestKotlin {
    @Autowired
    lateinit var categoryRepository: CategoryRepository

    @Autowired
    lateinit var beerRepository: BeerRepository

    var testBeer: Beer = Beer()

    @BeforeEach
    fun setUp() {
        testBeer = beerRepository.findAll()[0]
    }

    @Transactional
    @Test
    fun testAddCategory() {
        val savedCategory = categoryRepository.save(
            Category(
                description = "Ales"
            )
        )

        testBeer.addCategory(savedCategory)

        val savedBeer = beerRepository.save(testBeer)
        println(savedBeer.name)
    }
}