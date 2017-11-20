package de.tarent.axon.commands

import de.tarent.axon.domain.TicTacToeGame
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.commandhandling.model.Repository
import org.springframework.stereotype.Service

@Service
open class TicTacToeCommandHandler(private val repository: Repository<TicTacToeGame>) {

    @Suppress("unused")
    @CommandHandler
    fun handleStartGame(startGame: StartGame) {
        repository.newInstance { TicTacToeGame(startGame.gameUuid) }
    }

    @Suppress("unused")
    @CommandHandler
    fun handleCrossPlays(crossPlays: CrossPlays) {
        repository.load(crossPlays.gameUuid.toString()).invoke { game ->
            game.crossPlays(crossPlays.field)
        }
    }

    @Suppress("unused")
    @CommandHandler
    fun handleCirclePlays(circlePlays: CirclePlays) {
        repository.load(circlePlays.gameUuid.toString()).invoke { game ->
            game.circlePlays(circlePlays.field)
        }
    }
}