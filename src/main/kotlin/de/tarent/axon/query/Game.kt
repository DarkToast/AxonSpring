package de.tarent.axon.query

import java.util.*
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
data class Game (
    @Id
    val gameUuid: UUID,

    val version: Long,

    @OneToMany(cascade = arrayOf(CascadeType.ALL), orphanRemoval = true)
    val movesFromX: List<Movement>,

    @OneToMany(cascade = arrayOf(CascadeType.ALL), orphanRemoval = true)
    val movesFromO: List<Movement>,

    val lastMoveFrom: Char,

    val finished: Boolean
) {

    // Just for JPA
    @Suppress("unused")
    private constructor() : this(UUID.randomUUID(), 0L, emptyList<Movement>(), emptyList<Movement>(), 'X', false)

    override fun toString(): String {
        val state = arrayOf(
            arrayOf('-', '-', '-'),
            arrayOf('-', '-', '-'),
            arrayOf('-', '-', '-')
        )

        movesFromX.forEach({ move ->
            state[move.row][move.column] = 'X'
        })
        movesFromO.forEach({ move ->
            state[move.row][move.column] = 'O'
        })

        return state.fold("") { acc, elem ->
            acc + " " + elem.joinToString(" | ") + "\n"
        }.trimEnd()
    }
}