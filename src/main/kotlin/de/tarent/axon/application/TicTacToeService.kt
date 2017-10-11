package de.tarent.axon.application

import de.tarent.axon.domain.TicTacToeGame
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.commandhandling.model.Aggregate
import org.axonframework.commandhandling.model.Repository
import org.springframework.stereotype.Service
import java.util.*

@Service
open class TicTacToeService(val repository: Repository<TicTacToeGame>) {

    @CommandHandler
    fun handleStartGame(startGame: StartGameCommand): UUID {
        return repository.newInstance { TicTacToeGame(UUID.randomUUID()) }.identifier() as UUID
    }

    @CommandHandler
    fun getActualGame(getGameCommand: GetActualGameCommand): TicTacToeGameRead {
        val aggregate = repository.load(getGameCommand.gameUuid.toString())

        return aggregate.invoke { game ->
            TicTacToeGameRead(game.getGameUuid(), game.getVersion(), game.state)
        }
    }

    @CommandHandler
    fun handleStartGame(crossPlays: CrossPlaysCommand) {
        repository.load(crossPlays.gameUuid.toString()).invoke { game ->
            game.crossPlays(crossPlays.field)
        }
    }
}