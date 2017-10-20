package de.tarent.axon.application

import de.tarent.axon.application.stats.TicTacToeGameRead
import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec
import java.util.*

class TicTacToeGameReadTest : StringSpec() {
    init {

        "toString works" {
            val actualState: Array<Array<Char>> = arrayOf(
                arrayOf('-', 'X', '-'),
                arrayOf('-', '-', 'O'),
                arrayOf('O', '-', '-')
            )

            val expected = """> - | X | -
                              > - | - | O
                              > O | - | -
                           """.trimMargin(">")

            TicTacToeGameRead(UUID.randomUUID(), 1L, actualState).toString() shouldBe expected
        }

    }
}