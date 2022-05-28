package chess

const val BOARD_SIZE = 8

class Exit(message: String) : Exception(message)

/**
 * Entry point and game loop.
 *
 */
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

/**
 * Prompt users to create their players.
 *
 * @return pair of players
 */
fun promptPlayers(): Pair<Player, Player> {
    println("First Player's name:")
    val name1 = readln()
    val player1 = Player(name1, Color.WHITE)


    println("Second Player's name:")
    val name2 = readln()
    val player2 = Player(name2, Color.BLACK)

    return Pair(player1, player2)
}

/**
 * Prompt players to make a move
 *
 * @param player player to make a move
 * @return move of the player
 */
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

