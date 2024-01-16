Feature: Moving Pieces

  Scenario: Moving a knight
    Given an empty board
    And a knight on "f6"
    When the knight moves from "f6" to "e8"
    Then the knight is on "e8"
    And the knight is not on "f6"♦

    # put a piece on an invalid square