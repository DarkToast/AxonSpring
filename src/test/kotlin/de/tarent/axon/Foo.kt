package de.tarent.axon

import de.tarent.axon.domain.GameCreateEvent
import de.tarent.axon.domain.TicTacToeGame
import de.tarent.axon.movements.app.StartGameCommand
import de.tarent.axon.movements.app.TicTacToeCommandHandler
import io.kotlintest.specs.StringSpec
import org.axonframework.test.aggregate.AggregateTestFixture
import java.util.*

class FooTest : StringSpec() {
    lateinit var fixture: AggregateTestFixture<TicTacToeGame>


    init {
        "A create command result in a game create event" {
            setupAxon()

            val gameUuid = UUID.randomUUID()

            fixture
                .`when`(StartGameCommand(gameUuid))
                .expectSuccessfulHandlerExecution()
                .expectEvents(GameCreateEvent(gameUuid, 0L, aStartState(), 'X'))
        }
    }

    fun aStartState(): Array<Array<Char>> {
        return arrayOf(
            arrayOf('-', '-', '-'),
            arrayOf('-', '-', '-'),
            arrayOf('-', '-', '-')
        )
    }

    fun setupAxon() {
        fixture = AggregateTestFixture(TicTacToeGame::class.java)
        val commandHandler = TicTacToeCommandHandler(fixture.repository)
        fixture.registerAnnotatedCommandHandler(commandHandler)
    }
}