package de.tarent.axon.movements.app

import de.tarent.axon.domain.TicTacToeGame
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.commandhandling.model.Repository
import org.springframework.stereotype.Service

@Service
open class TicTacToeCommandHandler(private val repository: Repository<TicTacToeGame>) {

    @Suppress("unused")
    @CommandHandler
    fun handleStartGame(startGame: StartGameCommand) {
        repository.newInstance { TicTacToeGame(startGame.gameUuid) }
    }

    @Suppress("unused")
    @CommandHandler
    fun handleCrossPlays(crossPlays: CrossPlaysCommand) {
        repository.load(crossPlays.gameUuid.toString()).invoke { game ->
            game.crossPlays(crossPlays.field)
        }
    }
}