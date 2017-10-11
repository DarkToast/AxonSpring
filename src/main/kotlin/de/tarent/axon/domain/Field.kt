package de.tarent.axon.domain

data class Field(val row: Int, val column: Int) {
    init {
        if(row < 1 || row > 3) {
            throw IllegalArgumentException("row must be between 1 and 3")
        }

        if(column < 1 || column > 3) {
            throw IllegalArgumentException("column must be between 1 and 3")
        }
    }
}