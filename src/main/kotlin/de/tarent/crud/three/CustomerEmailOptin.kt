package de.tarent.crud.three

import java.util.*

// tag::presentation1[]
data class CustomerCreate(val name: String, val surname: String, val email: String)

data class CustomerUpdate(val name: String, val surname: String)

data class Customer(val id: UUID, val name: String, val surname: String, val email: String)

data class EmailChange(val email: String)
// end::presentation1[]

// tag::presentation2[]
interface CustomerService {
    fun create(customer: CustomerCreate): Customer
    fun update(customer: CustomerUpdate): Customer
    fun delete(customerUuid: UUID)
    fun changeEmail(customerUuid: UUID, emailChange: EmailChange)

    fun read(customerUuid: UUID): Customer
    fun getAllEmails(): List<EmailChange>
}
// end::presentation2[]