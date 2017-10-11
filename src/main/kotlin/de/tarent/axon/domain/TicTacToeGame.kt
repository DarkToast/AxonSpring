package de.tarent.axon.domain

import org.axonframework.commandhandling.model.AggregateIdentifier
import org.axonframework.commandhandling.model.AggregateLifecycle
import org.axonframework.commandhandling.model.AggregateRoot
import org.axonframework.eventhandling.EventHandler
import java.util.*

@AggregateRoot
open class TicTacToeGame() {

    @AggregateIdentifier
    private lateinit var gameUuid: UUID

    val state: Array<Array<Char>> = arrayOf(
        arrayOf('-', '-', '-'),
        arrayOf('-', '-', '-'),
        arrayOf('-', '-', '-')
    )

    private var version: Long = 0L



    constructor(gameUuid: UUID) : this() {
        this.gameUuid = gameUuid
        AggregateLifecycle.apply(GameCreateEvent(gameUuid))
    }



    fun crossPlays(field: Field) {
        if (state[field.row][field.column] == '-') {
            state[field.row][field.column] = 'X'
        } else {
            throw IllegalStateException("FooBar")
        }
    }

    fun circlePlays(field: Field) {
        if (state[field.row][field.column] == '-') {
            state[field.row][field.column] = 'O'
        } else {
            throw IllegalStateException("FooBar")
        }
    }

    @EventHandler(payloadType = GameCreateEvent::class)
    fun createEventHandler(event: GameCreateEvent) {
        this.gameUuid = event.gameUuid
        this.version++
    }

    fun getGameUuid(): UUID {
        return UUID.fromString(gameUuid.toString())
    }

    fun getVersion(): Long {
        return version + 0
    }
}