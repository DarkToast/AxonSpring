package de.tarent.axon.query

import de.tarent.axon.domain.CirclePlayed
import de.tarent.axon.domain.CrossPlayed
import de.tarent.axon.domain.GameFinished
import de.tarent.axon.domain.GameStarted
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component
import java.util.*

@Component
open class GameEventHandler(private val repository: GameRepository) {

    @EventHandler
    @Suppress("unused")
    fun handleGameStarted(event: GameStarted) {
        repository.save(
            Game(event.gameUuid, event.version, emptyList(), emptyList(), '-', false)
        )
    }

    @EventHandler
    @Suppress("unused")
    fun handleCrossPlayed(event: CrossPlayed) {
        val game = repository.findOne(event.gameUuid)

        val movement = Movement(UUID.randomUUID(), event.gameUuid, event.field.row, event.field.column, event.version)
        val newGame = game.copy(version = event.version, movesFromX = game.movesFromX.plus(movement), lastMoveFrom = 'X')
        repository.save(newGame)
    }

    @EventHandler
    @Suppress("unused")
    fun handleCirclePlayed(event: CirclePlayed) {
        val game = repository.findOne(event.gameUuid)

        val movement = Movement(UUID.randomUUID(), event.gameUuid, event.field.row, event.field.column, event.version)
        val newGame = game.copy(version = event.version, movesFromO = game.movesFromO.plus(movement), lastMoveFrom = 'O')
        repository.save(newGame)
    }

    @EventHandler
    @Suppress("unused")
    fun handleGameFinished(event: GameFinished) {
        val game = repository.findOne(event.gameUuid)
        repository.save(game.copy(finished = true))
    }
}