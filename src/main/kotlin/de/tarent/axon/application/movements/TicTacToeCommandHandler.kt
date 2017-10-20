package de.tarent.axon.application.movements

import de.tarent.axon.domain.TicTacToeGame
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.commandhandling.model.Repository
import org.springframework.stereotype.Service

@Service
open class TicTacToeCommandHandler(private val repository: Repository<TicTacToeGame>) {

    @CommandHandler
    fun handleStartGame(startGame: StartGameCommand) {
        repository.newInstance { TicTacToeGame(startGame.gameUuid) }
    }

    @CommandHandler
    fun handleCrossPlays(crossPlays: CrossPlaysCommand) {
        repository.load(crossPlays.gameUuid.toString()).invoke { game ->
            game.crossPlays(crossPlays.field)
        }
    }
}