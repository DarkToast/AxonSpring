package de.tarent.axon

import de.tarent.axon.commands.CirclePlays
import de.tarent.axon.commands.CrossPlays
import de.tarent.axon.commands.StartGame
import de.tarent.axon.domain.CirclePlayed
import de.tarent.axon.domain.CrossPlayed
import de.tarent.axon.domain.Field
import de.tarent.axon.domain.GameStarted
import org.junit.Test

class GameSpec: BaseSpecification() {

    @Test
    fun aGameCanBeStarted() {
        // Given
        val gameUuid = aGameUuid()

        fixture
            .`when`(StartGame(gameUuid))
            .expectSuccessfulHandlerExecution()
            .expectEvents(GameStarted(gameUuid, 1L, 'X'))
    }

    @Test
    fun crossCanMakeAGameMove() {
        // Given
        val gameUuid = aGameUuid()

        fixture
            .given(aGameStartedEvent(gameUuid))
            .`when`(CrossPlays(gameUuid, Field(1, 1)))
            .expectSuccessfulHandlerExecution()
            .expectEvents(CrossPlayed(gameUuid, 2, Field(1, 1)))
    }

    @Test
    fun crossAndCirclePlaysOneAfterAnother() {
        // Given
        val gameUuid = aGameUuid()

        fixture
            .given (
                aGameStartedEvent(gameUuid),
                CrossPlayed(gameUuid, 2, Field(1, 1))
            )
            .`when`(CirclePlays(gameUuid, Field(2, 1)))
            .expectSuccessfulHandlerExecution()
            .expectEvents(CirclePlayed(gameUuid, 3, Field(2, 1)))
    }

    @Test
    fun aSetFieldAndNotPlayed() {
        // Given
        val gameUuid = aGameUuid()

        fixture
            .given (
                aGameStartedEvent(gameUuid),
                CrossPlayed(gameUuid, 2, Field(1, 1))
            )
            .`when`(CirclePlays(gameUuid, Field(1, 1)))
            .expectNoEvents()
            .expectException(IllegalStateException::class.java)
    }

    @Test
    fun aPartyCanNotPlayOneAfterAnother() {
        // Given
        val gameUuid = aGameUuid()

        fixture
            .given (
                aGameStartedEvent(gameUuid),
                CrossPlayed(gameUuid, 2, Field(1, 1))
            )
            .`when`(CrossPlays(gameUuid, Field(2, 1)))
            .expectNoEvents()
            .expectException(IllegalStateException::class.java)
    }

    @Test
    fun onlyCrossCanMakeTheFirstMove() {
        // Given
        val gameUuid = aGameUuid()

        fixture
            .given (aGameStartedEvent(gameUuid))
            .`when`(CirclePlays(gameUuid, Field(2, 1)))
            .expectNoEvents()
            .expectException(IllegalStateException::class.java)
    }
}