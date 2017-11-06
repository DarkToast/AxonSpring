package de.tarent.axon.domain

import java.util.*

data class GameCreateEvent(val gameUuid: UUID, val version: Long, val createdState: Array<Array<Char>>, val actualParty: Char)