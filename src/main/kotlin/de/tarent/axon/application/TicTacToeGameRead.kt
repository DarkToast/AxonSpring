package de.tarent.axon.application

import java.util.*

data class TicTacToeGameRead(
    val gameUuid: UUID,
    val version: Long,
    val state:  Array<Array<Char>>
) {

    override fun toString(): String {
        state.reduce { acc, elem ->

        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TicTacToeGameRead

        if (gameUuid != other.gameUuid) return false
        if (version != other.version) return false
        if (!Arrays.equals(state, other.state)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = gameUuid.hashCode()
        result = 31 * result + version.hashCode()
        result = 31 * result + Arrays.hashCode(state)
        return result
    }
}