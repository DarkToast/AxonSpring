package de.tarent.axon.query

import de.tarent.axon.domain.GameCreateEvent
import de.tarent.axon.query.db.GameRepository
import de.tarent.axon.query.db.TicTacToeGameRead
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component

@Component
open class GameEventHandler(private val repository: GameRepository) {

    @EventHandler
    @Suppress("unused")
    fun handleCreateGame(event: GameCreateEvent) {
        repository.save(
                TicTacToeGameRead(event.gameUuid, event.version, event.createdState, event.actualParty)
        )
    }
}