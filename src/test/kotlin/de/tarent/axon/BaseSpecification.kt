package de.tarent.axon

import de.tarent.axon.commands.TicTacToeCommandHandler
import de.tarent.axon.domain.GameStarted
import de.tarent.axon.domain.TicTacToeGame
import org.axonframework.test.aggregate.AggregateTestFixture
import org.junit.Before
import java.util.*

abstract class BaseSpecification {

    lateinit var fixture: AggregateTestFixture<TicTacToeGame>

    @Before
    fun setupAxon() {
        fixture = AggregateTestFixture(TicTacToeGame::class.java)
        val commandHandler = TicTacToeCommandHandler(fixture.repository)
        fixture.registerAnnotatedCommandHandler(commandHandler)
    }

    protected fun aGameUuid(): UUID {
        return UUID.randomUUID()
    }

    protected fun aGameStartedEvent(gameUuid: UUID): GameStarted {
        return GameStarted(gameUuid, 1L, 'X')
    }

    protected fun aStartState(): Array<Array<Char>> {
        return arrayOf(
                arrayOf('-', '-', '-'),
                arrayOf('-', '-', '-'),
                arrayOf('-', '-', '-')
        )
    }
}