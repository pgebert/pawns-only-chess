package chess

import kotlin.math.abs

const val BOARD_SIZE = 8

class Exit(message: String) : Exception(message)

enum class Color(val value: String) {
    BLACK("B"),
    WHITE("W"),
    EMPTY(" ")
}

fun main() {

    println("Pawns-Only Chess")

    val (player1, player2) = promptPlayers()

    val board = Board()
    println(board)

    var currentPlayer = player1

    while (true) {

        try {
            val move = promptMove(currentPlayer)
            board.apply(move)
            println(board)

            board.checkWinningCondition(currentPlayer)

            currentPlayer = when (currentPlayer) {
                player1 -> player2
                else -> player1
            }
        } catch (e: Exit) {
            println(e.message)
            break
        } catch (e: Exception) {
            println(e.message)
        }
    }

}

data class Player(val name: String, val color: Color)

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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Position) return false

        if (col != other.col) return false
        if (row != other.row) return false

        return true
    }

    override fun toString(): String {
        val colAsChar = CharRange('a', 'z').toList()[col]
        val rowAsChar = (BOARD_SIZE - row).digitToChar()
        return "$colAsChar$rowAsChar"
    }

}

data class Move(val player: Player, val start: Position, val destination: Position)

class Board {
    private val history = mutableListOf<Move>()

    private val field = MutableList(BOARD_SIZE) {
        MutableList(BOARD_SIZE) {
            Color.EMPTY
        }
    }

    init {
        field[1] = MutableList(BOARD_SIZE) { Color.BLACK }
        field[BOARD_SIZE - 2] = MutableList(BOARD_SIZE) { Color.WHITE }
    }

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

    fun isStalemate(player: Player): Boolean {

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


fun promptPlayers(): Pair<Player, Player> {
    println("First Player's name:")
    val name1 = readln()
    val player1 = Player(name1, Color.WHITE)


    println("Second Player's name:")
    val name2 = readln()
    val player2 = Player(name2, Color.BLACK)

    return Pair(player1, player2)
}

fun promptMove(player: Player): Move {
    println("${player.name}'s turn:")
    val input = readln()

    val pattern = Regex("[a-h][1-8][a-h][1-8]")

    if (input == "exit") throw Exit("Bye!")
    if (!input.matches(pattern)) throw Exception("Invalid Input")

    return Move(
        player = player,
        start = Position(input[0], input[1]),
        destination = Position(input[2], input[3])
    )
}

