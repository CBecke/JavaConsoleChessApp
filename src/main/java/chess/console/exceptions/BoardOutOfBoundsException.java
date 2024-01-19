package chess.console.exceptions;

public class BoardOutOfBoundsException extends Exception {
    public BoardOutOfBoundsException(String errorMessage) {
        super(errorMessage);
    }
}
