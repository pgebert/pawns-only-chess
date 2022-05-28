package chess

/**
 * Color of a player or field.
 *
 * @property value the string representation
 * @constructor Create a new Color
 */
enum class Color(val value: String) {
    BLACK("B"),
    WHITE("W"),
    EMPTY(" ")
}