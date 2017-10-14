package de.tarent.axon.application

import de.tarent.axon.domain.Field
import java.util.UUID

abstract class Command(val gameUuid: UUID)


class StartGameCommand(newGameUuid: UUID) : Command(newGameUuid)

class CrossPlaysCommand(gameUuid: UUID, val field: Field) : Command(gameUuid)

class CirclePlaysCommand(gameUuid: UUID, val field: Field) : Command(gameUuid)

class GetActualGameCommand(gameUuid: UUID) : Command(gameUuid)