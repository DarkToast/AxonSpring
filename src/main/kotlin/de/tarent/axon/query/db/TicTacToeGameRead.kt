package de.tarent.axon.query.db

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class TicTacToeGameRead(@Id val gameUuid: UUID, val version: Long, val state:  Array<Array<Char>>, val party: Char) {

    private constructor(): this(UUID.randomUUID(), 0L, emptyArray(), 'X')

    override fun toString(): String {
        return state.fold("") { acc, elem ->
            acc + " " + elem.joinToString(" | ") + "\n"
        }.trimEnd()
    }


}