package de.tarent.axon.ports.rest

import de.tarent.axon.application.*
import de.tarent.axon.domain.Field
import de.tarent.axon.domain.TicTacToeGame
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod.*
import org.springframework.web.context.request.async.DeferredResult
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.*

typealias GameResponse = ResponseEntity<TicTacToeGameRead>

@Controller
open class TictacToeController(private val commandGateway: CommandGateway) {

    @RequestMapping(value = "/game", method = arrayOf(POST))
    fun startGame(): DeferredResult<GameResponse> {
        val result = DeferredResult<GameResponse>()

        commandGateway.send<UUID>(StartGameCommand()).thenAccept({ gameUuid: UUID ->
            val uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/game/$gameUuid").build().toUri()
            result.setResult(ResponseEntity.created(uri).build())
        })

        return result
    }

    @RequestMapping(value = "/game/{gameUuid}", method = arrayOf(GET))
    fun actualGame(@PathVariable gameUuid: String): DeferredResult<GameResponse> {
        return sendCommand(GetActualGameCommand(UUID.fromString(gameUuid)), DeferredResult())
    }

    @RequestMapping(value = "/game/{gameUuid}/version/{version}", method = arrayOf(GET))
    fun versionedGame(@PathVariable gameUuid: String): DeferredResult<GameResponse> {
        return sendCommand(GetActualGameCommand(UUID.fromString(gameUuid)), DeferredResult())
    }

    @RequestMapping(value = "/game/{gameUuid}/cross", method = arrayOf(PUT))
    fun crossPlays(@PathVariable gameUuid: String, @RequestBody field: Field): DeferredResult<GameResponse> {
        return sendCommand(
            CrossPlaysCommand(UUID.fromString(gameUuid), field),
            DeferredResult()
        )
    }

    @RequestMapping(value = "/game/{gameUuid}/cicle", method = arrayOf(PUT))
    fun circlePlays(@PathVariable gameUuid: String, @RequestBody field: Field): DeferredResult<GameResponse> {
        return sendCommand(
            CirclePlaysCommand(UUID.fromString(gameUuid), field),
            DeferredResult()
        )
    }

    private fun sendCommand(command: Command, result: DeferredResult<GameResponse>): DeferredResult<GameResponse> {
        commandGateway.send<TicTacToeGameRead>(command).thenAccept({ game ->
            result.setResult(ResponseEntity.ok(game))
        })

        return result
    }
}