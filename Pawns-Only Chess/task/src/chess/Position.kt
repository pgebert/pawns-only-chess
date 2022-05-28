package chess

/**
 * Position on the board.
 *
 * @constructor Create a position on the board.
 */
class Position {
    var col = 0
    var row = 0

    constructor(col: Char, row: Char) {
        this.col = CharRange('a', 'z').indexOf(col)
        this.row = BOARD_SIZE - row.digitToInt()
    }

    constructor(col: Int, row: Int) {
        this.col = col
        this.row = row
    }

    /**
     * Equals
     *
     * @param other object to compare
     * @return whether this and other are equal
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Position) return false

        if (col != other.col) return false
        if (row != other.row) return false

        return true
    }

    /**
     * To string
     *
     * @return string representation of this position
     */
    override fun toString(): String {
        val colAsChar = CharRange('a', 'z').toList()[col]
        val rowAsChar = (BOARD_SIZE - row).digitToChar()
        return "$colAsChar$rowAsChar"
    }

}