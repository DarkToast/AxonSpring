package de.tarent.axon.query

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface GameRepository: JpaRepository<Game, UUID>