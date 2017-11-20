package de.tarent.axon.ports.rest

import de.tarent.axon.query.GameRepository
import de.tarent.axon.query.TicTacToeGameRead
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.*

@Controller
class QueryController(private val repository: GameRepository) {

    @RequestMapping(value = "/game/{gameUuid}", method = arrayOf(RequestMethod.GET), headers = arrayOf("Accept=text/plain"))
    fun actualGameString(@PathVariable gameUuid: UUID): ResponseEntity<String> {
        return ResponseEntity.ok(repository.findOne(gameUuid).toString())
    }

    @RequestMapping(value = "/game/{gameUuid}", method = arrayOf(RequestMethod.GET), headers = arrayOf("Accept=application/json"))
    fun actualGameJson(@PathVariable gameUuid: UUID): ResponseEntity<TicTacToeGameRead> {
        return ResponseEntity.ok(repository.findOne(gameUuid))
    }

    @RequestMapping(value = "/game", method = arrayOf(RequestMethod.GET), headers = arrayOf("Accept=application/json"))
    fun getGames(): ResponseEntity<List<Link>> {
        return ResponseEntity.ok(repository.findAll().map { game ->
            Link(game.gameUuid)
        })
    }

    data class Link(val gameUuid: UUID) {
        val href = ServletUriComponentsBuilder.fromCurrentContextPath().path("/game/$gameUuid").build().toUri()
    }
}