package de.tarent.axon.application

import de.tarent.axon.domain.TicTacToeGame
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.commandhandling.model.Repository
import org.springframework.stereotype.Service
import java.util.*

@Service
open class TicTacToeService(val repository: Repository<TicTacToeGame>) {

    @CommandHandler
    fun handleStartGame(startGame: StartGameCommand) {
        repository.newInstance { TicTacToeGame(startGame.newGameUuid) }
    }

    @CommandHandler
    fun handleCrossPlays(crossPlays: CrossPlaysCommand) {
        repository.load(crossPlays.gameUuid.toString()).invoke { game ->
            game.crossPlays(crossPlays.field)
        }
    }
}