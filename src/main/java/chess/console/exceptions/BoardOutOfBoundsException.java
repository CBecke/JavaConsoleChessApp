package chess.console.exceptions;

public class BoardOutOfBoundsException extends Exception {
    public BoardOutOfBoundsException(String square) {
        super("Given square '" + square + "' is not in range a1-h8");
    }
}
