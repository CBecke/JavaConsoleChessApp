package stepDefinitions;

import chess.console.Board;
import chess.console.Knight;
import chess.console.exceptions.BoardOutOfBoundsException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class movingSteps {
    private Board board;
    private Knight knight;
    private boolean expectException;
    private BoardOutOfBoundsException exception;

    @Given("an empty board")
    public void anEmptyBoard() {
        board = new Board();
    }

    @And("a knight on {string}")
    public void aKnightOn(String square) throws BoardOutOfBoundsException {
        knight = new Knight();
        board.put(knight, square);
    }

    @When("the knight moves from {string} to {string}")
    public void theKnightMovesTo(String squareFrom, String squareTo) {
        board.move(squareFrom, squareTo);
    }

    @Then("the knight is on {string}")
    public void theKnightIsOn(String square) {
        assertEquals(knight, board.get(square));
    }


    @And("the knight is not on {string}")
    public void theKnightIsNotOn(String square) {
        assertNull(board.get(square));
    }

    @And("a knight illegally on {string}")
    public void aKnightIllegallyOn(String square) throws BoardOutOfBoundsException {
        knight = new Knight();
        exception = assertThrows(BoardOutOfBoundsException.class, () -> {
            board.put(knight, square);
        });
    }

    @Then("throw error {string}")
    public void throwError(String errorMessage) {
        assertNotNull(exception);
        assertEquals(errorMessage, exception.getMessage());
    }

}
