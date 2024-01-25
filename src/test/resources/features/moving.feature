Feature: Moving Pieces

  # Moving with a knight
  Scenario: Moving a knight
    Given an empty board
    And a knight on "f6"
    And the square "e8" is empty
    When the knight moves from "f6" to "e8"
    Then the knight is on "e8"
    And the square "f6" is empty

  Scenario: Putting a knight on an out of bounds file (fail)
    Given an empty board
    And a knight illegally on "i6"
    Then throw error "Given square 'i6' is not in range a1-h8"

  Scenario: Putting a knight on an out of bounds rank (fail)
    Given an empty board
    And a knight illegally on "a0"
    Then throw error "Given square 'a0' is not in range a1-h8"

  Scenario: Putting a knight on an invalid file (fail)
    Given an empty board
    And a knight illegally on "ab1"
    Then throw error "Given square 'ab1' is not in range a1-h8"

  Scenario: Putting a knight on an invalid rank (fail)
    Given an empty board
    And a knight illegally on "a11"
    Then throw error "Given square 'a11' is not in range a1-h8"

  Scenario: Moving a knight to an invalid square (fail)
    Given an empty board
    And a knight on "a8"
    When the knight moves illegally from "h8" to "h9"
    Then throw error "Given square 'h9' is not in range a1-h8"

  Scenario: Trying to move a piece from an empty square (fail)
    Given an empty board
    When the non-existent knight moves illegally from "a2" to "b4"
    Then throw error "The square moved from is empty"

  Scenario: Trying to move a knight to a square it can't move to
    Given an empty board
    And a knight on "a1"
    And the square "b4" is empty
    When the knight moves from "a1" to "b4"
    Then the knight is on "a1"
    And the square "b4" is empty

  Scenario: Capturing a piece
    Given an empty board
    And a white knight on "a1"
    And a black knight on "c2"
    When the knight on "a1" captures on "c2"
    And the square "a1" is empty

  Scenario: Capturing a piece of the same color (fail)
    Given an empty board
    And a white knight on "a1"
    And another white knight on "c2"
    When the knight on "a1" captures on "c2"
    Then the knight stays on "a1"
    And the other knight stays on "c2"

  Scenario: Knight jumping over pieces
    Given an empty board
    And a white knight on "a1"
    And another white knight on "a2"
    When the knight moves from "a1" to "b3"
    Then the knight is on "b3"
    And the square "a1" is empty

  # Moving with a bishop
  Scenario: Moving a bishop up+right
    Given an empty board
    And a black bishop on "b2"
    When the bishop moves from "b2" to "g7"
    Then the bishop is on "g7"
    And the square "b2" is empty

  Scenario: Moving a bishop down+left
    Given an empty board
    And a black bishop on "g7"
    When the bishop moves from "g7" to "b2"
    Then the bishop is on "b2"
    And the square "g7" is empty

  Scenario: Moving a bishop up+left
    Given an empty board
    And a black bishop on "g2"
    When the bishop moves from "g2" to "b7"
    Then the bishop is on "b7"
    And the square "g2" is empty

  Scenario: Moving a bishop down+right
    Given an empty board
    And a black bishop on "b2"
    When the bishop moves from "b2" to "g7"
    Then the bishop is on "g7"
    And the square "b2" is empty

  Scenario: Moving a bishop to illegal square
    Given an empty board
    And a black bishop on "a2"
    When the bishop moves from "a2" to "a3"
    Then the bishop is on "a2"
    And the square "a3" is empty

  Scenario: Capturing with a bishop
    Given an empty board
    And a black bishop on "c1"
    And a white knight on "a3"
    When the bishop on "c1" captures on "a3"
    Then the bishop is on "a3"
    And the square "c1" is empty

  Scenario: Trying to move through another piece with a bishop
    Given an empty board
    And a black bishop on "a3"
    And a white knight on "b2"
    When the bishop moves from "a3" to "c1"
    Then the bishop is on "a3"
    And the knight is on "b2"
    And the square "c1" is empty


  # Moving with a king
  Scenario: Moving a king vertically
    Given an empty board
    And a white king on "h1"
    When the king moves from "h1" to "h2"
    Then the king is on "h2"
    And the square "h1" is empty

  Scenario: Moving a king diagonally
    Given an empty board
    And a white king on "h2"
    When the king moves from "h2" to "g1"
    Then the king is on "g1"
    And the square "h2" is empty

  Scenario: Moving a king horizontally
    Given an empty board
    And a white king on "g2"
    When the king moves from "g2" to "h2"
    Then the king is on "h2"
    And the square "g2" is empty

  Scenario: still-moving a king
    Given an empty board
    And a white king on "h1"
    When the king moves from "h1" to "h1"
    Then the king is on "h1"

  Scenario: Capturing with a king
    Given an empty board
    And a white king on "h1"
    And a black knight on "h2"
    When the king moves from "h1" to "h2"
    Then the king is on "h2"
    And the square "h1" is empty

  Scenario: Trying to capture same color piece with king
    Given an empty board
    And a white king on "h1"
    And a white knight on "h2"
    When the king moves from "h1" to "h2"
    Then the king is on "h1"
    And the knight is on "h2"

  Scenario: Trying to move king horizontally into check
    Given an empty board
    And a black bishop on "b2"
    And a white king on "c2"
    When the king moves from "c2" to "c3"
    Then the king is on "c2"
    And the square "c3" is empty

  Scenario: trying to move king vertically into check
    Given an empty board
    And a black bishop on "b2"
    And a white king on "e4"
    When the king moves from "e4" to "d4"
    Then the king is on "e4"
    And the square "d4" is empty

  Scenario: Trying to move king more than 1 square
    Given an empty board
    And a white king on "e4"
    When the king moves from "e4" to "e2"
    Then the king is on "e4"
    And the square "e2" is empty

  # Moving with a rook
  Scenario: Moving a rook horizontally
    Given an empty board
    And a black rook on "h8"
    When the rook moves from "h8" to "h1"
    Then the rook is on "h1"
    And the square "h8" is empty

  Scenario: Trying to move rook diagonally
    Given an empty board
    And a black rook on "h8"
    When the rook moves from "h8" to "g7"
    Then the rook is on "h8"
    And the square "g7" is empty

  Scenario: Moving a rook vertically
    Given an empty board
    And a black rook on "h8"
    When the rook moves from "h8" to "h1"
    Then the rook is on "h1"
    And the square "h8" is empty

  Scenario: Capturing with a rook
    Given an empty board
    And a black rook on "h8"
    And a white knight on "h1"
    When the rook moves from "h8" to "h1"
    Then the rook is on "h1"
    And the square "h8" is empty

  Scenario: Trying to move through an opposite color piece with a rook
    Given an empty board
    And a black rook on "h8"
    And a white knight on "h2"
    When the rook moves from "h8" to "h1"
    Then the rook is on "h8"
    And the square "h1" is empty

  Scenario: Trying to move through a same color piece with a rook
    Given an empty board
    And a black rook on "h8"
    And a black knight on "h2"
    When the rook moves from "h8" to "h1"
    Then the rook is on "h8"
    And the square "h1" is empty


    # Moving with a queen
    # diagonally
  Scenario: Moving a queen up+right
    Given an empty board
    And a white queen on "b2"
    When the queen moves from "b2" to "g7"
    Then the queen is on "g7"
    And the square "b2" is empty

  Scenario: Moving a queen down+left
    Given an empty board
    And a white queen on "g7"
    When the queen moves from "g7" to "b2"
    Then the queen is on "b2"
    And the square "g7" is empty

  Scenario: Moving a queen up+left
    Given an empty board
    And a white queen on "g2"
    When the queen moves from "g2" to "b7"
    Then the queen is on "b7"
    And the square "g2" is empty

  Scenario: Moving a queen down+right
    Given an empty board
    And a white queen on "b2"
    When the queen moves from "b2" to "g7"
    Then the queen is on "g7"
    And the square "b2" is empty

  Scenario: Trying to move through another piece with a queen
    Given an empty board
    And a white queen on "a3"
    And a white knight on "b2"
    When the queen moves from "a3" to "c1"
    Then the queen is on "a3"
    And the knight is on "b2"
    And the square "c1" is empty


  # horizontally / vertically
  Scenario: Moving a queen horizontally
    Given an empty board
    And a white queen on "h8"
    When the queen moves from "h8" to "h1"
    Then the queen is on "h1"
    And the square "h8" is empty

  Scenario: Moving a queen vertically
    Given an empty board
    And a white queen on "h8"
    When the queen moves from "h8" to "h1"
    Then the queen is on "h1"
    And the square "h8" is empty

  Scenario: Trying to move through an opposite color piece with a queen
    Given an empty board
    And a white queen on "h8"
    And a white knight on "h2"
    When the queen moves from "h8" to "h1"
    Then the queen is on "h8"
    And the square "h1" is empty

  Scenario: Trying to move through a same color piece with a queen
    Given an empty board
    And a white queen on "h8"
    And a black knight on "h2"
    When the queen moves from "h8" to "h1"
    Then the queen is on "h8"
    And the square "h1" is empty

  # Misc
  Scenario: Capturing with a queen
    Given an empty board
    And a white queen on "h8"
    And a black rook on "h1"
    When the queen moves from "h8" to "h1"
    Then the queen is on "h1"
    And the square "h8" is empty


  # castling
  Scenario: White Queen side castling
    Given an empty board
    And a white king on "e1"
    And the king can castle
    And a white rook on "a1"
    And the rook can castle
    When the king moves from "e1" to "c1"
    Then the king is on "c1"
    And the rook is on "d1"

  Scenario: White king side castling
    Given an empty board
    And a white king on "e1"
    And the king can castle
    And a white rook on "h1"
    And the rook can castle
    When the king moves from "e1" to "g1"
    Then the king is on "g1"
    And the rook is on "f1"

  Scenario: black queen side castling
    Given an empty board
    And a black king on "e8"
    And the king can castle
    And a black rook on "a8"
    And the rook can castle
    When the king moves from "e8" to "c8"
    Then the king is on "c8"
    And the rook is on "d8"

  Scenario: black king side castling
    Given an empty board
    And a black king on "e8"
    And the king can castle
    And a black rook on "h8"
    And the rook can castle
    When the king moves from "e8" to "g8"
    Then the king is on "g8"
    And the rook is on "f8"

  Scenario: Trying to castle when the king has already moved
    Given an empty board
    And a white king on "e1"
    And the king has moved from "e1" to "d1"
    And the king has moved from "d1" to "e1"
    And a white rook on "h1"
    And the rook can castle
    When the king moves from "e1" to "g1"
    Then the king is on "e1"
    And the rook is on "h1"

  Scenario: Trying to castle when the rook has already moved
    Given an empty board
    And a white king on "e1"
    And the king can castle
    And a white rook on "a1"
    And the rook has moved from "a1" to "b1"
    And the rook has moved from "b1" to "a1"
    When the king moves from "e1" to "c1"
    Then the king is on "e1"
    And the rook is on "a1"

  Scenario: castle when the OTHER rook has already moved
    Given an empty board
    And a white king on "e1"
    And the king can castle
    And a white rook on "a1"
    And the rook has moved from "a1" to "b1"
    And the rook has moved from "b1" to "a1"
    And another white rook on "h1"
    When the king moves from "e1" to "g1"
    Then the king is on "g1"
    And the other rook is on "f1"

  Scenario: castle when path is attacked by same color piece
    Given an empty board
    And a white king on "e1"
    And the king can castle
    And a white rook on "a1"
    And the rook can castle
    And another white rook on "d2"
    When the king moves from "e1" to "c1"
    Then the king is on "c1"
    And the rook is on "d1"

  Scenario: trying to castle when path is attacked
    Given an empty board
    And a white king on "e1"
    And the king can castle
    And a white rook on "a1"
    And the rook can castle
    And a black bishop on "a2"
    When the king moves from "e1" to "c1"
    Then the king is on "e1"
    And the rook is on "a1"

  Scenario: moving a white pawn 2 steps forward from initial square
    Given an empty board
    And a white pawn on "b2"
    When the pawn moves from "b2" to "b4"
    Then the pawn is on "b4"
    And the square "b2" is empty

  Scenario: trying to move a white pawn 2 steps forward from non-initial square
    Given an empty board
    And a white pawn on "b3"
    When the pawn moves from "b3" to "b5"
    Then the pawn is on "b3"
    And the square "b5" is empty

  Scenario: moving a black pawn 2 steps forward from initial square
    Given an empty board
    And a black pawn on "b7"
    When the pawn moves from "b7" to "b5"
    Then the pawn is on "b5"
    And the square "b7" is empty

  Scenario: trying to move a black pawn 2 steps forward from non-initial square
    Given an empty board
    And a black pawn on "b6"
    When the pawn moves from "b6" to "b4"
    Then the pawn is on "b6"
    And the square "b4" is empty

  Scenario: trying to move a white pawn backwards
    Given an empty board
    And a white pawn on "b2"
    When the pawn moves from "b2" to "b1"
    Then the pawn is on "b2"
    And the square "b1" is empty

  Scenario: trying to move a black pawn backwards
    Given an empty board
    And a black pawn on "b6"
    When the pawn moves from "b6" to "b7"
    Then the pawn is on "b6"
    And the square "b7" is empty

  Scenario: Moving a white pawn 1 step forward
    Given an empty board
    And a white pawn on "b2"
    When the pawn moves from "b2" to "b3"
    Then the pawn is on "b3"
    And the square "b2" is empty

  Scenario: trying to move a black pawn 1 step backward
    Given an empty board
    And a black pawn on "b2"
    When the pawn moves from "b2" to "b3"
    Then the pawn is on "b2"
    And the square "b3" is empty

  Scenario: Moving a black pawn 1 step forward
    Given an empty board
    And a black pawn on "b7"
    When the pawn moves from "b7" to "b6"
    Then the pawn is on "b6"
    And the square "b7" is empty

  Scenario: trying to move a white pawn 1 step backward
    Given an empty board
    And a white pawn on "b7"
    When the pawn moves from "b7" to "b6"
    Then the pawn is on "b7"
    And the square "b6" is empty


    # can't take piece vertically with pawn
    # can take piece diagonally with pawn
    # pawn promotion
    # check and checkmate
    # Find a better way to set canCastle to false for rook and king, instead of isKing/RookMove() in Board.move()



