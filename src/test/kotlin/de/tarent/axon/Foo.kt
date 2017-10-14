package de.tarent.axon

import de.tarent.axon.application.StartGameCommand
import de.tarent.axon.domain.GameCreateEvent
import de.tarent.axon.domain.TicTacToeGame
import io.kotlintest.specs.StringSpec
import org.axonframework.test.aggregate.AggregateTestFixture
import java.util.*

class FooTest : StringSpec() {
    lateinit var fixture: AggregateTestFixture<TicTacToeGame>

    init {
        "foobar" {
            fixture = AggregateTestFixture(TicTacToeGame::class.java)

            val gameUuid = UUID.randomUUID()

            fixture
                .`when`(StartGameCommand(gameUuid))
                .expectSuccessfulHandlerExecution()
                .expectEvents(GameCreateEvent(gameUuid))
        }
    }
}