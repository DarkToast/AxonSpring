package de.tarent.axon.domain

data class Field(val row: Int, val column: Int) {
    init {
        if(row < 0 || row > 2) {
            throw IllegalArgumentException("row must be between 0 and 2")
        }

        if(column < 0 || column > 2) {
            throw IllegalArgumentException("column must be between 0 and 2")
        }
    }
}