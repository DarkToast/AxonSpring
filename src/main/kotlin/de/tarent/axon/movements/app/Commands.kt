package de.tarent.axon.movements.app

import de.tarent.axon.domain.Field
import java.util.*

abstract class Command(val gameUuid: UUID)

class StartGameCommand(newGameUuid: UUID) : Command(newGameUuid)

class CrossPlaysCommand(gameUuid: UUID, val field: Field) : Command(gameUuid)

class CirclePlaysCommand(gameUuid: UUID, val field: Field) : Command(gameUuid)