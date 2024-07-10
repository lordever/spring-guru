package com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.repositories

import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.entities.Customer
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CustomerRepository: JpaRepository<Customer, UUID> {
}