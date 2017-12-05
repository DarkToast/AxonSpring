package de.tarent.axon.domain

import de.tarent.axon.domain.Party.O
import de.tarent.axon.domain.Party.X
import org.axonframework.commandhandling.model.AggregateIdentifier
import org.axonframework.commandhandling.model.AggregateLifecycle
import org.axonframework.eventhandling.EventHandler
import org.axonframework.spring.stereotype.Aggregate
import java.util.*

@Aggregate
open class TicTacToeGame() {

    @AggregateIdentifier
    private lateinit var gameUuid: UUID

    private val state: Array<Array<Char>> = arrayOf(
        arrayOf('-', '-', '-'),
        arrayOf('-', '-', '-'),
        arrayOf('-', '-', '-')
    )

    private var version: Long = 0L

    private var nextParty: Party = X

    private var gameFinished: Boolean = false

    private var victoryParty: Char = '-'



    constructor(gameUuid: UUID) : this() {
        this.gameUuid = gameUuid
        AggregateLifecycle.apply(GameStarted(gameUuid, 1L, nextParty.symbol))
    }

    fun crossPlays(field: Field) {
        checkStateForNewMove(X, field)
        AggregateLifecycle.apply(CrossPlayed(gameUuid, ++version, field))
    }

    fun circlePlays(field: Field) {
        checkStateForNewMove(O, field)
        AggregateLifecycle.apply(CirclePlayed(gameUuid, ++version, field))
    }

    private fun checkStateForNewMove(party: Party, field: Field) {
        if(gameFinished) {
            throw IllegalStateException("The game is finished!")
        }

        if(nextParty != party) {
            throw IllegalStateException("It's not your turn $party!")
        }

        if (state[field.row][field.column] != '-') {
            throw IllegalStateException("Field already set.")
        }
    }

    private fun checkForVictory() {
        fun check(): Char {

            // check rows
            state.forEach { row ->
                if (row.all { field -> field != '-' }) {
                    return row[0]
                }
            }

            // Check columns
            if (state.all { row -> row[0] != '-' }) {
                return state[0][0]
            }
            if (state.all { row -> row[1] != '-' }) {
                return state[0][1]
            }
            if (state.all { row -> row[2] != '-' }) {
                return state[0][2]
            }

            // Check diags
            if (state[0][0] != '-' && state[1][1] != '-' && state[2][2] != '-') {
                return state[0][0]
            }

            if (state[0][2] != '-' && state[1][1] != '-' && state[2][0] != '-') {
                return state[0][2]
            }

            return '-'
        }

        val victoryParty = check()
        if(victoryParty != '-') {
            AggregateLifecycle.apply(GameFinished(gameUuid, ++version, victoryParty))
        }
    }

    @EventHandler
    fun gameStartedHandler(event: GameStarted) {
        this.gameUuid = event.gameUuid
        this.version = event.version
    }

    @EventHandler
    fun crossPlayedHandler(event: CrossPlayed) {
        val field = event.field

        this.state[field.row][field.column] = X.symbol
        this.nextParty = O
        this.version = event.version

        checkForVictory()
    }

    @EventHandler
    fun circlePlayedHandler(event: CirclePlayed) {
        val field = event.field

        this.state[field.row][field.column] = O.symbol
        this.nextParty = X
        this.version = event.version

        checkForVictory()
    }

    @EventHandler
    fun gameFinishedHandler(event: GameFinished) {
        this.gameFinished = true
        this.victoryParty = event.victoryParty
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

    fun getActualParty(): Party {
        return nextParty
    }
}