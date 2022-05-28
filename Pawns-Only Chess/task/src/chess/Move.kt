package chess

/**
 * Move of a figure on the board.
 *
 * @property player who wants to move a figure
 * @property start start position on the board
 * @property destination end position on the board
 * @constructor Create new move
 */
data class Move(
    val player: Player,
    val start: Position,
    val destination: Position
)