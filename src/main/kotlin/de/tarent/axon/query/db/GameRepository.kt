package de.tarent.axon.query.db

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface GameRepository: JpaRepository<TicTacToeGameRead, UUID>