package stepDefinitions;

import chess.console.Board;
import chess.console.pieces.*;
import chess.console.exceptions.BoardOutOfBoundsException;
import chess.console.exceptions.IllegalMoveException;
import chess.console.pieces.pawn.BlackPawn;
import chess.console.pieces.pawn.Pawn;
import chess.console.pieces.pawn.WhitePawn;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class movingSteps {
    Board board;
    private Knight knight;
    private Exception exception;
    private Knight capturedKnight;
    private Bishop bishop;
    private King king;
    private Rook rook;
    private Queen queen;
    private Rook otherRook;
    private Pawn pawn;

    @Given("an empty board")
    public void anEmptyBoard() { board = new Board(); }

    @And("a knight on {string}")
    public void aKnightOn(String square) throws BoardOutOfBoundsException {
        knight = new Knight(Knight.Color.WHITE);
        board.put(knight, square);
    }

    @When("the knight moves from {string} to {string}")
    public void theKnightMovesTo(String squareFrom, String squareTo) throws BoardOutOfBoundsException, IllegalMoveException {
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
        knight = new Knight(Knight.Color.WHITE);
        exception = assertThrows(BoardOutOfBoundsException.class, () -> {
            board.put(knight, square);
        });
    }

    @Then("throw error {string}")
    public void throwError(String errorMessage) {
        assertNotNull(exception);
        assertEquals(errorMessage, exception.getMessage());
    }

    @When("the knight moves illegally from {string} to {string}")
    public void theKnightMovesIllegallyFromTo(String squareFrom, String squareTo) throws BoardOutOfBoundsException {
        exception = assertThrows(BoardOutOfBoundsException.class, () -> {
            board.move(squareFrom, squareTo);
        });
    }

    @When("the non-existent knight moves illegally from {string} to {string}")
    public void theNonExistentKnightMovesIllegallyFromTo(String squareFrom, String squareTo) {
        exception = assertThrows(IllegalMoveException.class, () -> {
            board.move(squareFrom, squareTo);
        });
    }

    @And("the square {string} is empty")
    public void theSquareIsEmpty(String square) {
        assertTrue(board.isEmpty(square));
    }

    @And("another white knight on {string}")
    public void anotherWhiteKnightOn(String square) throws BoardOutOfBoundsException {
        capturedKnight = new Knight(Knight.Color.WHITE);
        board.put(capturedKnight, square);
    }

    @Then("the knight on {string} captures on {string}")
    public void theKnightOnCapturesOn(String squareFrom, String squareTo) throws IllegalMoveException, BoardOutOfBoundsException {
        board.move(squareFrom, squareTo);
    }

    @And("a white knight on {string}")
    public void aWhiteKnightOn(String square) throws BoardOutOfBoundsException {
        knight = new Knight(Knight.Color.WHITE);
        board.put(knight, square);
    }

    @And("a black knight on {string}")
    public void aBlackKnightOn(String square) throws BoardOutOfBoundsException {
        capturedKnight = new Knight(Knight.Color.BLACK);
        board.put(capturedKnight, square);
    }

    @Then("the knight stays on {string}")
    public void theKnightStaysOn(String square) {
        assertEquals(knight, board.get(square));
    }

    @And("the other knight stays on {string}")
    public void theOtherKnightStaysOn(String square) {
        assertEquals(capturedKnight, board.get(square));
    }


    @And("a black bishop on {string}")
    public void aBlackBishopOn(String square) throws BoardOutOfBoundsException {
        bishop = new Bishop(Bishop.Color.BLACK);
        board.put(bishop, square);
    }

    @When("the bishop moves from {string} to {string}")
    public void theBishopMovesFromTo(String squareFrom, String squareTo) throws IllegalMoveException, BoardOutOfBoundsException {
        board.move(squareFrom, squareTo);
    }

    @Then("the bishop is on {string}")
    public void theBishopIsOn(String square) {
        assertEquals(bishop, board.get(square));
    }

    @When("the bishop on {string} captures on {string}")
    public void theBishopOnCapturesOn(String squareFrom, String squareTo) throws IllegalMoveException, BoardOutOfBoundsException {
        board.move(squareFrom, squareTo);
    }

    @And("a white king on {string}")
    public void aWhiteKingOn(String square) throws BoardOutOfBoundsException {
        king = new King(Piece.Color.WHITE);
        board.put(king, square);
    }

    @When("the king moves from {string} to {string}")
    public void theKingMovesFromTo(String squareFrom, String squareTo) throws IllegalMoveException, BoardOutOfBoundsException {
        board.move(squareFrom, squareTo);
    }

    @Then("the king is on {string}")
    public void theKingIsOn(String square) {
        assertEquals(king, board.get(square));
    }

    @And("a black rook on {string}")
    public void aBlackRookOn(String square) throws BoardOutOfBoundsException {
        rook = new Rook(Piece.Color.BLACK);
        board.put(rook, square);
    }

    @When("the rook moves from {string} to {string}")
    public void theRookMovesFromTo(String squareFrom, String squareTo) throws IllegalMoveException, BoardOutOfBoundsException {
        board.move(squareFrom, squareTo);
    }

    @Then("the rook is on {string}")
    public void theRookIsOn(String square) {
        assertEquals(rook, board.get(square));
    }

    @And("a white queen on {string}")
    public void aWhiteQueenOn(String square) throws BoardOutOfBoundsException {
        queen = new Queen(Piece.Color.WHITE);
        board.put(queen, square);
    }

    @When("the queen moves from {string} to {string}")
    public void theQueenMovesFromTo(String squareFrom, String squareTo) throws IllegalMoveException, BoardOutOfBoundsException {
        board.move(squareFrom, squareTo);
    }

    @Then("the queen is on {string}")
    public void theQueenIsOn(String square) {
        assertEquals(queen, board.get(square));
    }

    @And("a white rook on {string}")
    public void aWhiteRookOn(String square) throws BoardOutOfBoundsException {
        rook = new Rook(Piece.Color.WHITE);
        board.put(rook, square);
    }

    @And("the king can castle")
    public void theKingCanCastle() {
        assertTrue(king.canCastle());
    }

    @And("the rook can castle")
    public void theRookCanCastle() {
        assertTrue(rook.canCastle());
    }

    @And("a black king on {string}")
    public void aBlackKingOn(String square) throws BoardOutOfBoundsException {
        king = new King(Piece.Color.BLACK);
        board.put(king, square);
    }

    @And("the king has moved from {string} to {string}")
    public void theKingHasMovedFromTo(String squareFrom, String squareTo) throws IllegalMoveException, BoardOutOfBoundsException {
        board.move(squareFrom, squareTo);
    }

    @And("the rook has moved from {string} to {string}")
    public void theRookHasMovedFromTo(String squareFrom, String squareTo) throws IllegalMoveException, BoardOutOfBoundsException {
        board.move(squareFrom, squareTo);
    }

    @And("another white rook on {string}")
    public void anotherWhiteRookOn(String square) throws BoardOutOfBoundsException {
        otherRook = new Rook(Piece.Color.WHITE);
        board.put(otherRook, square);
    }

    @And("the other rook is on {string}")
    public void theOtherRookIsOn(String square) {
        assertEquals(otherRook, board.get(square));
    }

    @And("a white pawn on {string}")
    public void aWhitePawnOn(String square) throws BoardOutOfBoundsException {
        pawn = new WhitePawn();
        board.put(pawn, square);
    }

    @When("the pawn moves from {string} to {string}")
    public void thePawnMovesFromTo(String squareFrom, String squareTo) throws IllegalMoveException, BoardOutOfBoundsException {
        board.move(squareFrom, squareTo);
    }

    @Then("the pawn is on {string}")
    public void thePawnIsOn(String square) {
        assertEquals(pawn, board.get(square));
    }

    @And("a black pawn on {string}")
    public void aBlackPawnOn(String square) throws BoardOutOfBoundsException {
        pawn = new BlackPawn();
        board.put(pawn, square);
    }
}
