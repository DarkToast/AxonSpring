package de.tarent.axon.domain

data class Field(val row: Int, val column: Int) {
    init {
        if(row < 0 || row > 2) {
            throw IllegalArgumentException("row must be between 1 and 3")
        }

        if(column < 0 || column > 2) {
            throw IllegalArgumentException("column must be between 1 and 3")
        }
    }
}