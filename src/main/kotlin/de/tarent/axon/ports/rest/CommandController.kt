package de.tarent.axon.ports.rest

import de.tarent.axon.commands.CirclePlays
import de.tarent.axon.commands.CrossPlays
import de.tarent.axon.commands.StartGame
import de.tarent.axon.domain.Field
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
open class CommandController(private val commandGateway: CommandGateway) {

    @RequestMapping(value = "/game", method = arrayOf(POST))
    fun startGame(): DeferredResult<*> {

        val result = DeferredResult<Any>()
        val newGameUuid = UUID.randomUUID()

        // Creating a new command object
        val command = StartGame(newGameUuid)

        // Sending the command into the CQRS system.
        val future: CompletableFuture<Any> = commandGateway.send<Any>(command)

        // Return the uuid, when the command handling is finished.
        future
            .exceptionally({ e -> result.setResult(ResponseEntity.badRequest().body(e.message)) })
            .thenAccept({ addUriToResult(result, newGameUuid, HttpStatus.SEE_OTHER) })

        return result
    }

    @RequestMapping(value = "/game/{gameUuid}/cross", method = arrayOf(PUT))
    fun crossPlays(@PathVariable gameUuid: String, @RequestBody moveRequest: MoveRequest): DeferredResult<*> {
        val result = DeferredResult<Any>()

        // Creating a new command object
        val command = CrossPlays(UUID.fromString(gameUuid), Field(moveRequest.row, moveRequest.column))

        // Sending the command into the CQRS system.
        val future: CompletableFuture<Any> = commandGateway.send<Any>(command)

        // Return the uuid, when the command handling is finished.
        future
            .exceptionally({ e -> result.setResult(ResponseEntity.badRequest().body(e.message)) })
            .thenAccept({ addUriToResult(result, command.gameUuid, HttpStatus.SEE_OTHER) })

        return result
    }

    @RequestMapping(value = "/game/{gameUuid}/circle", method = arrayOf(PUT))
    fun circlePlays(@PathVariable gameUuid: String, @RequestBody moveRequest: MoveRequest): DeferredResult<*> {
        val result = DeferredResult<Any>()

        // Creating a new command object
        val command = CirclePlays(UUID.fromString(gameUuid), Field(moveRequest.row, moveRequest.column))

        // Sending the command into the CQRS system.
        val future: CompletableFuture<Any> = commandGateway.send<Any>(command)

        // Return the uuid, when the command handling is finished.
        future
            .exceptionally({ e -> result.setResult(ResponseEntity.badRequest().body(e.message)) })
            .thenAccept({ addUriToResult(result, command.gameUuid, HttpStatus.SEE_OTHER) })

        return result
    }

    private fun addUriToResult(result: DeferredResult<Any>, gameUuid: UUID, httpStatus: HttpStatus) {
        val uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/game/$gameUuid").build().toUri()
        result.setResult(ResponseEntity.status(httpStatus).location(uri).build<GameResponse>())
    }
}