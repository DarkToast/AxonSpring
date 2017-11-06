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

    private val state: Array<Array<Char>> = arrayOf(
        arrayOf('-', '-', '-'),
        arrayOf('-', '-', '-'),
        arrayOf('-', '-', '-')
    )

    private var version: Long = 0L

    private var actualParty: Char = 'X'



    constructor(gameUuid: UUID) : this() {
        this.gameUuid = gameUuid
        AggregateLifecycle.apply(GameCreateEvent(gameUuid, version, state, actualParty))
    }

    fun crossPlays(field: Field) {
        if(actualParty != 'X') {
            throw IllegalStateException("It's not your turn X!")
        }

        if (state[field.row][field.column] == '-') {
            state[field.row][field.column] = 'X'
            actualParty = 'X'
        } else {
            throw IllegalStateException("FooBar")
        }
    }

    fun circlePlays(field: Field) {
        if(actualParty != 'O') {
            throw IllegalStateException("It's not your turn O!")
        }

        if (state[field.row][field.column] == '-') {
            state[field.row][field.column] = 'O'
            actualParty = 'O'
        } else {
            throw IllegalStateException("FooBar")
        }
    }



    fun getGameUuid(): UUID {
        return UUID.fromString(gameUuid.toString())
    }

    fun getVersion(): Long {
        return version + 0
    }

    fun getState(): Array<Array<Char>> {
        return state.copyOf()
    }

    fun getActualParty(): Char {
        return actualParty
    }


    @EventHandler(payloadType = GameCreateEvent::class)
    fun createEventHandler(event: GameCreateEvent) {
        this.gameUuid = event.gameUuid
        this.version++
    }
}