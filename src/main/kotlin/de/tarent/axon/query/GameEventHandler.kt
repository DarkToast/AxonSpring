package de.tarent.axon.query

import de.tarent.axon.domain.CirclePlayed
import de.tarent.axon.domain.CrossPlayed
import de.tarent.axon.domain.GameStarted
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component

@Component
open class GameEventHandler(private val repository: GameRepository) {

    @EventHandler
    @Suppress("unused")
    fun handleGameStarted(event: GameStarted) {
        repository.save(
            TicTacToeGameRead(event.gameUuid, event.version, emptyList(), emptyList(), event.startParty)
        )
    }

    @EventHandler
    @Suppress("unused")
    fun handleCrossPlayed(event: CrossPlayed) {
        val game = repository.findOne(event.gameUuid)
        val newGame = game.copy(version = event.version, xMoves = game.xMoves.plus(event.field), party = 'X')
        repository.save(newGame)
    }

    @EventHandler
    @Suppress("unused")
    fun handleCirclePlayed(event: CirclePlayed) {
        val game = repository.findOne(event.gameUuid)
        val newGame = game.copy(version = event.version, oMoves = game.oMoves.plus(event.field), party = 'O')
        repository.save(newGame)
    }
}