package de.tarent.crud.two

import java.util.*

// tag::presentation[]
interface CustomerService {
    fun create(customer: CustomerCreate): Customer
    fun update(customerUuid: UUID, customer: CustomerCreate): Customer
    fun delete(customerUuid: UUID)

    fun read(customerUuid: UUID): Customer
    fun getAllEmails(): List<Customer>
}

data class CustomerCreate (val name: String, val surname: String, val email: String)

data class Customer (val id: UUID, val name: String, val surname: String, val email: String)
// end::presentation[]