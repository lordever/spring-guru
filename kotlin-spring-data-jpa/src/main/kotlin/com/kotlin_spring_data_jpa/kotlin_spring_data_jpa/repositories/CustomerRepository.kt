package com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.repositories

import com.kotlin_spring_data_jpa.kotlin_spring_data_jpa.entities.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CustomerRepository: JpaRepository<Customer, UUID> {
}