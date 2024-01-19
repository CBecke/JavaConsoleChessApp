Feature: Moving Pieces

  Scenario: Moving a knight
    Given an empty board
    And a knight on "f6"
    When the knight moves from "f6" to "e8"
    Then the knight is on "e8"
    And the knight is not on "f6"


  Scenario: Putting a knight on an out of bounds file (fail)
    Given an empty board
    And a knight illegally on "i6"
    Then throw error "Given square 'i6' is not in range a0-h8"

  # test invalid square: 2 letters
  # test invalid square: 2 numbers
