package de.tarent.axon.domain

import java.util.*

data class GameStarted(val gameUuid: UUID, val version: Long, val startParty: Char)

data class CrossPlayed(val gameUuid: UUID, val version: Long, val field: Field)

data class CirclePlayed(val gameUuid: UUID, val version: Long, val field: Field)