package de.tarent.crud.one

import java.util.*

// tag::presentation[]
interface CustomerService {
    fun create(customer: Customer): Customer
    fun update(customer: Customer): Customer
    fun read(customerUuid: UUID): Customer
    fun delete(customerUuid: UUID)
}

data class Customer (
    val id: UUID, val name: String, val surname: String, val email: String
)

// end::presentation[]