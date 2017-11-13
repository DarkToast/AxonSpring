package de.tarent.cqrs

import java.util.*

// tag::presentation[]
data class CreateCustomer(val name: String, val surname: String, val email: String)

data class UpdateCustomer(val customerUuid: UUID, val name: String, val surname: String)

data class DeleteCustomer(val customerUuid: UUID)

data class ChangeEmail(val customerUuid: UUID, val newEmail: String)
// end::presentation[]