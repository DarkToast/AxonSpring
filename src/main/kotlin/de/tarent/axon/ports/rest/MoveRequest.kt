package de.tarent.axon.ports.rest

data class MoveRequest(val row: Int, val column: Int) {
    // Just for JPA
    @Suppress("unused")
    private constructor(): this(0, 0)
}