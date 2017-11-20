package de.tarent.axon.commands

import de.tarent.axon.domain.Field
import java.util.*

abstract class Command(val gameUuid: UUID)

class StartGame(newGameUuid: UUID) : Command(newGameUuid)

class CrossPlays(gameUuid: UUID, val field: Field) : Command(gameUuid)

class CirclePlays(gameUuid: UUID, val field: Field) : Command(gameUuid)