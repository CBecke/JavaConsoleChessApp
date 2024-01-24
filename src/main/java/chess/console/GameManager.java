package chess.console;

import chess.console.exceptions.BoardOutOfBoundsException;
import chess.console.exceptions.IllegalMoveException;
import chess.console.pieces.Piece;

/**
 * Singleton class to combine and handle game components. Retrieve instance with GameManager.getInstance()
 */
@Deprecated // Not useful for now
public class GameManager {
    /// Singleton components
    private static GameManager instance = null;
    private GameManager() {}

    public static GameManager getInstance() {
        if (instance == null) {instance = new GameManager(); }
        return instance;
    }

    // Logic
    private final Board board = new Board();


    public void clearBoard() {
        board.clear();
    }

    public void put(Piece piece, String square) throws BoardOutOfBoundsException {
        board.put(piece, square);
    }

    public void move(Piece piece, String squareFrom, String squareTo) throws IllegalMoveException, BoardOutOfBoundsException {
        board.move(piece, squareFrom, squareTo);
    }

    public Piece getPiece(String square) { return board.get(square); }

    public boolean isEmpty(String square) { return board.isEmpty(square); }
}
