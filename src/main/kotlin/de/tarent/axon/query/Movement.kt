package de.tarent.axon.query

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Movement(
    @Id
    val movementId: UUID,
    val gameUuid: UUID,
    val row: Int,
    val column: Int,
    val version: Long
) {
    // Just for JPA
    @Suppress("unused")
    private constructor(): this(UUID.randomUUID(), UUID.randomUUID(), 0, 0, 0)
}