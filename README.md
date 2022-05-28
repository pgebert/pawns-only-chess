# Pawns-only Chess

<img src="https://user-images.githubusercontent.com/6838540/170736651-ba0ef4d8-d0f3-4b77-8b77-732708c9cfca.png" >

### Prerequisites

* \>=JDK 11.0.14

### Getting started 

```
./gradlew build
./gradlew run --console=plain
```

### Description

**Beginning**: To start the game, please enter your names. The first player automatically takes the color white and the second one black. To **move** enter the start and end field in the form <start_col><start_row><end_col><end_row> (e.g. e2e4).

**Capture** is a move of a pawn when an opposite color pawn stands one square to the left or right on a diagonal. The attacking pawn takes the position of the captured pawn; the captured pawn is taken off the chessboard.

![whiteCapture](https://user-images.githubusercontent.com/6838540/170735868-fdb3f23a-0cab-408f-a9b7-489dcab52d52.png)

White's turn. All possible captures.

![blackCapture](https://user-images.githubusercontent.com/6838540/170735886-db51a688-aa2b-4e31-93ac-44fc9b986402.png)

Black's turn. All possible captures.

**En passant**  is a special type of pawn capture. A white pawn is on the 5th rank. A black pawn is on the adjacent file (a vertical line); the black pawn (this should be its first move in the game) moves 2 squares forward, passing the white pawn. The white pawn can capture the black pawn by moving forward diagonally. The capture should be done right away, otherwise, the right to the en passant capture is lost:

![whiteEnPassant](https://user-images.githubusercontent.com/6838540/170736466-859d08ad-cb80-49c0-819c-e55885bd3c4e.png)

Move sequence. White captures en passant.
For Black, the situation is the direct opposite. A black pawn is on the 4th rank. A white pawn is on the adjacent file (a vertical line); the white pawn (this should be its first move in the game) moves 2 squares forward passing the black pawn. The black pawn can capture the white pawn by moving forward diagonally. The capture should be done right away, otherwise, the right to the en passant capture is lost:

![blackEnPassant](https://user-images.githubusercontent.com/6838540/170736471-1373df32-1c75-48f1-941b-fc7e28cd8c47.png)

Move sequence. Black captures en passant.

A player **wins** if he captures all the pawns of the opponent or reaches the opposite ending of the field with one of its own pawns. If no player can make a move anymore, its a **stalemate**.

More on pawn captures can be found at chess.com

### Examples

Example 1: White wins by moving a pawn across the whole board

```
Pawns-Only Chess
First Player's name:
> Amelia
Second Player's name:
> Gregory
  +---+---+---+---+---+---+---+---+
8 |   |   |   |   |   |   |   |   |
  +---+---+---+---+---+---+---+---+
7 | B | B | B | B | B | B | B | B |
  +---+---+---+---+---+---+---+---+
6 |   |   |   |   |   |   |   |   |
  +---+---+---+---+---+---+---+---+
5 |   |   |   |   |   |   |   |   |
  +---+---+---+---+---+---+---+---+
4 |   |   |   |   |   |   |   |   |
  +---+---+---+---+---+---+---+---+
3 |   |   |   |   |   |   |   |   |
  +---+---+---+---+---+---+---+---+
2 | W | W | W | W | W | W | W | W |
  +---+---+---+---+---+---+---+---+
1 |   |   |   |   |   |   |   |   |
  +---+---+---+---+---+---+---+---+
    a   b   c   d   e   f   g   h

Amelia's turn:
> e2e4
  +---+---+---+---+---+---+---+---+
8 |   |   |   |   |   |   |   |   |
  +---+---+---+---+---+---+---+---+
7 | B | B | B | B | B | B | B | B |
  +---+---+---+---+---+---+---+---+
6 |   |   |   |   |   |   |   |   |
  +---+---+---+---+---+---+---+---+
5 |   |   |   |   |   |   |   |   |
  +---+---+---+---+---+---+---+---+
4 |   |   |   |   | W |   |   |   |
  +---+---+---+---+---+---+---+---+
3 |   |   |   |   |   |   |   |   |
  +---+---+---+---+---+---+---+---+
2 | W | W | W | W |   | W | W | W |
  +---+---+---+---+---+---+---+---+
1 |   |   |   |   |   |   |   |   |
  +---+---+---+---+---+---+---+---+
    a   b   c   d   e   f   g   h

...
... (after some moves)
...


Gregory's turn:
> b7b6
  +---+---+---+---+---+---+---+---+
8 |   |   |   |   |   |   |   |   |
  +---+---+---+---+---+---+---+---+
7 |   |   |   | W | B | B | B | B |
  +---+---+---+---+---+---+---+---+
6 |   | B | B |   |   |   |   |   |
  +---+---+---+---+---+---+---+---+
5 | B |   |   |   |   |   |   |   |
  +---+---+---+---+---+---+---+---+
4 |   |   |   |   |   |   |   |   |
  +---+---+---+---+---+---+---+---+
3 |   |   |   |   |   |   |   |   |
  +---+---+---+---+---+---+---+---+
2 | W | W | W | W |   | W | W | W |
  +---+---+---+---+---+---+---+---+
1 |   |   |   |   |   |   |   |   |
  +---+---+---+---+---+---+---+---+
    a   b   c   d   e   f   g   h

Amelia's turn:
> d7d8
  +---+---+---+---+---+---+---+---+
8 |   |   |   | W |   |   |   |   |
  +---+---+---+---+---+---+---+---+
7 |   |   |   |   | B | B | B | B |
  +---+---+---+---+---+---+---+---+
6 |   | B | B |   |   |   |   |   |
  +---+---+---+---+---+---+---+---+
5 | B |   |   |   |   |   |   |   |
  +---+---+---+---+---+---+---+---+
4 |   |   |   |   |   |   |   |   |
  +---+---+---+---+---+---+---+---+
3 |   |   |   |   |   |   |   |   |
  +---+---+---+---+---+---+---+---+
2 | W | W | W | W |   | W | W | W |
  +---+---+---+---+---+---+---+---+
1 |   |   |   |   |   |   |   |   |
  +---+---+---+---+---+---+---+---+
    a   b   c   d   e   f   g   h

White Wins!
Bye!
```

Have fun playing it! ♟
