Feature: Moving Pieces

  # Moving with a knight
  Scenario: Moving a knight
    Given an empty board
    And a knight on "f6"
    And the square "e8" is empty
    When the knight moves from "f6" to "e8"
    Then the knight is on "e8"
    And the square "e8" is empty

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

  Scenario: Moving a bishop
  Given an empty board
  And a black bishop on "a2"
  When the bishop moves from "a2" to "g7"
  Then the bishop is on "g7"
  And the square "a2" is empty



      # trying and failing to move past/through another piece [with a piece different than knight]