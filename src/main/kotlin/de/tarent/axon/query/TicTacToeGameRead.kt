package de.tarent.axon.query

import de.tarent.axon.domain.Field
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ElementCollection

@Entity
data class TicTacToeGameRead(
    @Id val gameUuid: UUID,
    val version: Long,
    @ElementCollection val xMoves: List<Field>,
    @ElementCollection val oMoves: List<Field>,
    val party: Char
) {

    // Just for JPA
    @Suppress("unused")
    private constructor(): this(UUID.randomUUID(), 0L, emptyList<Field>(), emptyList<Field>(), 'X')

    override fun toString(): String {
        val state = arrayOf(
            arrayOf('-', '-', '-'),
            arrayOf('-', '-', '-'),
            arrayOf('-', '-', '-')
        )

        xMoves.forEach({move ->
            state[move.row][move.column] = 'X'
        })
        oMoves.forEach({move ->
            state[move.row][move.column] = 'O'
        })

        return state.fold("") { acc, elem ->
            acc + " " + elem.joinToString(" | ") + "\n"
        }.trimEnd()
    }


}