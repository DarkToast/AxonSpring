package de.tarent.axon.application

import de.tarent.axon.domain.Field
import java.util.UUID

abstract class Command

class StartGameCommand : Command()
data class CrossPlaysCommand(val gameUuid: UUID, val field: Field) : Command()
data class CirclePlaysCommand(val gameUuid: UUID, val field: Field) : Command()
data class GetActualGameCommand(val gameUuid: UUID) : Command()