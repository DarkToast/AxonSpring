package de.tarent.axon.ports.rest

import de.tarent.axon.application.stats.TicTacToeGameRead
import de.tarent.axon.domain.TicTacToeGame
import org.axonframework.commandhandling.model.Repository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
class QueryController(private val repository: Repository<TicTacToeGame>) {

    @RequestMapping(value = "/game/{gameUuid}", method = arrayOf(RequestMethod.GET))
    fun actualGame(@PathVariable gameUuid: String): ResponseEntity<TicTacToeGameRead> {
        val readGame = repository.load(gameUuid).invoke({ game ->
            TicTacToeGameRead(game.getGameUuid(), game.getVersion(), game.getState())
        })

        return ResponseEntity.ok(readGame)
    }

    @RequestMapping(value = "/game/{gameUuid}/version/{version}", method = arrayOf(RequestMethod.GET))
    fun versionedGame(@PathVariable gameUuid: String, @PathVariable version: Long): ResponseEntity<TicTacToeGameRead> {
        val readGame = repository.load(gameUuid, version).invoke({ game ->
            TicTacToeGameRead(game.getGameUuid(), game.getVersion(), game.getState())
        })

        return ResponseEntity.ok(readGame)
    }

}