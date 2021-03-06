package de.tarent.axon.ports.rest

import com.fasterxml.jackson.annotation.JsonIgnore
import de.tarent.axon.query.Game
import de.tarent.axon.query.GameRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import java.util.*

@Controller
class QueryController(private val repository: GameRepository) {

    @RequestMapping(value = "/game/{gameUuid}", method = arrayOf(RequestMethod.GET), headers = arrayOf("Accept=text/plain"))
    fun actualGameString(@PathVariable gameUuid: UUID): ResponseEntity<String> {
        return Optional.ofNullable(repository.findOne(gameUuid))
                .map {  game -> ResponseEntity.ok(game.toString()) }
                .orElse(ResponseEntity.notFound().build())
    }

    @RequestMapping(value = "/game/{gameUuid}", method = arrayOf(RequestMethod.GET), headers = arrayOf("Accept=application/json"))
    fun actualGameJson(@PathVariable gameUuid: UUID): ResponseEntity<JsonResponse> {
        return Optional.ofNullable(repository.findOne(gameUuid))
                .map {  game -> ResponseEntity.ok(JsonResponse(game, Link(game))) }
                .orElse(ResponseEntity.notFound().build())
    }

    @RequestMapping(value = "/game", method = arrayOf(RequestMethod.GET), headers = arrayOf("Accept=application/json"))
    fun getGames(): ResponseEntity<List<Link>> {
        return ResponseEntity.ok(repository.findAll().map { game -> Link(game) })
    }

    data class JsonResponse(val game: Game, val link: Link)

    data class Link(private @JsonIgnore val game: Game) {
        val gameUuid = game.gameUuid
        val links: MutableMap<String, URI> = hashMapOf()

        init {
            links.put("get", ServletUriComponentsBuilder.fromCurrentContextPath().path("/game/$gameUuid").build().toUri())

            if(game.lastMoveFrom == 'O' || game.lastMoveFrom == '-') {
                links.put("crossPlays", ServletUriComponentsBuilder.fromCurrentContextPath().path("/game/$gameUuid/cross").build().toUri())
            } else {
                links.put("circlePlays", ServletUriComponentsBuilder.fromCurrentContextPath().path("/game/$gameUuid/circle").build().toUri())
            }
        }
    }
}