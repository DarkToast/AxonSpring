package de.tarent.axon.movements.rest

import de.tarent.axon.domain.Field
import de.tarent.axon.movements.app.CirclePlaysCommand
import de.tarent.axon.movements.app.CrossPlaysCommand
import de.tarent.axon.movements.app.StartGameCommand
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod.POST
import org.springframework.web.bind.annotation.RequestMethod.PUT
import org.springframework.web.context.request.async.DeferredResult
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.*
import java.util.concurrent.CompletableFuture

typealias GameResponse = ResponseEntity<UUID>

@Controller
open class TicTacToeController(private val commandGateway: CommandGateway) {

    @RequestMapping(value = "/game", method = arrayOf(POST))
    fun startGame(): DeferredResult<GameResponse> {

        val result = DeferredResult<GameResponse>()
        val newGameUuid = UUID.randomUUID()

        // Creating a new command object
        val command = StartGameCommand(newGameUuid)

        // Sending the command into the CQRS system.
        val future: CompletableFuture<Any> = commandGateway.send<Any>(command)

        // Return the uuid, when the command handling is finished.
        future.thenAccept({ addUriToResult(result, newGameUuid, HttpStatus.CREATED) })

        return result
    }

    @RequestMapping(value = "/game/{gameUuid}/cross", method = arrayOf(PUT))
    fun crossPlays(@PathVariable gameUuid: String, @RequestBody moveRequest: MoveRequest): DeferredResult<GameResponse> {
        val result = DeferredResult<GameResponse>()

        // Creating a new command object
        val command = CrossPlaysCommand(UUID.fromString(gameUuid), Field(moveRequest.row, moveRequest.column))

        // Sending the command into the CQRS system.
        val future: CompletableFuture<Any> = commandGateway.send<Any>(command)

        // Return the uuid, when the command handling is finished.
        future.thenAccept({ addUriToResult(result, command.gameUuid, HttpStatus.SEE_OTHER) })

        return result
    }

    @RequestMapping(value = "/game/{gameUuid}/cicle", method = arrayOf(PUT))
    fun circlePlays(@PathVariable gameUuid: String, @RequestBody moveRequest: MoveRequest): DeferredResult<GameResponse> {
        val result = DeferredResult<GameResponse>()

        // Creating a new command object
        val command = CirclePlaysCommand(UUID.fromString(gameUuid), Field(moveRequest.row, moveRequest.column))

        // Sending the command into the CQRS system.
        val future: CompletableFuture<Any> = commandGateway.send<Any>(command)

        // Return the uuid, when the command handling is finished.
        future.thenAccept({ addUriToResult(result, command.gameUuid, HttpStatus.SEE_OTHER) })

        return result
    }

    private fun addUriToResult(result: DeferredResult<GameResponse>, gameUuid: UUID, httpStatus: HttpStatus) {
        val uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/game/$gameUuid").build().toUri()
        result.setResult(ResponseEntity.status(httpStatus).location(uri).build())
    }
}