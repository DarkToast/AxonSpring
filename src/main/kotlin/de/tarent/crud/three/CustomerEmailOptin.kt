package de.tarent.crud.three

import java.util.*

// tag::presentation1[]
data class CustomerCreate(val name: String, val surname: String, val email: String)

data class CustomerUpdate(val name: String, val surname: String)

data class Customer(val id: UUID, val name: String, val surname: String, val email: String)

data class Email(val email: String)
// end::presentation1[]

// tag::presentation2[]
interface CustomerService {
    fun create(customer: CustomerCreate): Customer

    fun update(customer: CustomerUpdate): Customer

    fun read(customerUuid: UUID): Customer

    fun delete(customerUuid: UUID)

    fun changeEmail(customerUuid: UUID, email: Email)
}
// end::presentation2[]