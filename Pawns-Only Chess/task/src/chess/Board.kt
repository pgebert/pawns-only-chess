package chess

import kotlin.math.abs

/**
 * Board representing a pawns-only chess board.
 *
 * @constructor Create a new board ready to play
 */
class Board {
    /**
     * History of applied moves.
     */
    private val history = mutableListOf<Move>()

    /**
     * State of the board with all the figures.
     */
    private val field = MutableList(BOARD_SIZE) {
        MutableList(BOARD_SIZE) {
            Color.EMPTY
        }
    }

    init {
        field[1] = MutableList(BOARD_SIZE) { Color.BLACK }
        field[BOARD_SIZE - 2] = MutableList(BOARD_SIZE) { Color.WHITE }
    }

    /**
     * Apply a move to the  current board state.
     *
     * @param move move to execute
     */
    fun apply(move: Move) {

        if (field[move.start.row][move.start.col] != move.player.color) {
            val color = move.player.color.name.lowercase()
            throw Exception("No $color pawn at ${move.start}")
        }

        val moveInSameColumn = move.start.col == move.destination.col
        val moveToNextColumn = abs(move.start.col - move.destination.col) == 1
        val moveToNextRow = abs(move.start.row - move.destination.row) == 1

        val invalidStepSize = when (move.player.color) {
            Color.WHITE -> !(move.start.row - move.destination.row == 1 || move.start.row - move.destination.row == 2 && move.start.row == BOARD_SIZE - 2)
            Color.BLACK -> !(move.destination.row - move.start.row == 1 || move.destination.row - move.start.row == 2 && move.start.row == 1)
            else -> false
        }

        val fieldIsEmpty = field[move.destination.row][move.destination.col] == Color.EMPTY
        val fieldIsOpponent = when (move.player.color) {
            Color.WHITE -> field[move.destination.row][move.destination.col] == Color.BLACK
            Color.BLACK -> field[move.destination.row][move.destination.col] == Color.WHITE
            else -> false
        }

        val normalMove = moveInSameColumn && fieldIsEmpty
        val takeOpponent = moveToNextColumn && moveToNextRow && fieldIsOpponent

        // En passant move can only be applied directly after the en passant situation occurred
        val enPassantSituationIsNew =
            history.isNotEmpty() && (history[history.lastIndex].destination == move.start || history[history.lastIndex].destination == Position(
                move.destination.col,
                move.start.row
            ))

        val enPassantSituation = when (move.player.color) {
            Color.WHITE -> move.start.row == 3 && move.destination.row == 2 && moveToNextColumn && field[move.start.row][move.destination.col] == Color.BLACK
            Color.BLACK -> move.start.row == 4 && move.destination.row == 5 && moveToNextColumn && field[move.start.row][move.destination.col] == Color.WHITE
            else -> false
        }

        val enPassant = enPassantSituation && enPassantSituationIsNew

        if (!(normalMove || takeOpponent || enPassant)
            || invalidStepSize
        ) {
            throw Exception("Invalid Input")
        }

        field[move.destination.row][move.destination.col] = field[move.start.row][move.start.col]
        field[move.start.row][move.start.col] = Color.EMPTY

        if (enPassant) {
            field[move.start.row][move.destination.col] = Color.EMPTY
        }

        history.add(move)
    }

    /**
     * Check whether a player has won.
     *
     * @param player player to check
     */
    fun checkWinningCondition(player: Player) {

        val message = when (player.color) {
            Color.WHITE -> "White wins!"
            Color.BLACK -> "Black wins!"
            else -> ""
        }

        val allCaptured = field.flatten().filterNot { it == Color.EMPTY || it == player.color }.isEmpty()

        val reachedLastRow = history[history.lastIndex].destination.row in listOf(0, BOARD_SIZE - 1)

        if (allCaptured || reachedLastRow) {
            println(message)
            throw Exit("Bye!")
        } else if (isStalemate(player)) {
            println("Stalemate!")
            throw Exit("Bye!")
        }

    }

    /**
     * Check whether the player is in a stalemate situation.
     *
     * @param player player to check
     * @return whether a stalemate occurred on the board
     */
    private fun isStalemate(player: Player): Boolean {

        val direction = if (player.color == Color.WHITE) 1 else -1
        for (row in 0 until BOARD_SIZE) {
            for (col in 0 until BOARD_SIZE) {

                try {
                    if (field[row][col] !in listOf(player.color, Color.EMPTY)
                        && (field[row + direction][col] == Color.EMPTY
                                || field[row + direction][col + 1] == player.color
                                || field[row + direction][col - 1] == player.color
                                )
                    )
                        return false
                } catch (e: IndexOutOfBoundsException) {
                }

            }
        }
        return true
    }

    /**
     * To string
     *
     * @return string representation of the board
     */
    override fun toString(): String {

        val letters = CharRange('a', 'z')

        var result = ""

        for ((index, row) in field.withIndex()) {

            val rowNumber = BOARD_SIZE - index

            result += "  +" + "---+".repeat(BOARD_SIZE) + "\n"
            result += "$rowNumber |" + row.joinToString("|") { " ${it.value} " } + "|\n"
        }

        result += "  +" + "---+".repeat(BOARD_SIZE) + "\n"
        result += "   " + letters.take(BOARD_SIZE).joinToString(" ") { " $it " } + "\n"

        return result
    }

}