package stepDefinitions;

import chess.console.Board;
import chess.console.Knight;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class movingSteps {
    private Board board;
    Knight knight;

    @Given("an empty board")
    public void anEmptyBoard() {
        board = new Board();
    }

    @And("a knight on {string}")
    public void aKnightOn(String position) {
        knight = new Knight();
        board.put(knight, position);
    }

    @When("the knight moves from {string} to {string}")
    public void theKnightMovesTo(String posFrom, String posTo) {
        board.move(posFrom, posTo);
    }

    @Then("the knight is on {string}")
    public void theKnightIsOn(String position) {
        assertEquals(board.get(position), knight);
    }

    @And("the knight is not on {string}♦")
    public void theKnightIsNotOn(String position) {
        assertNull(board.get(position));
    }
}
