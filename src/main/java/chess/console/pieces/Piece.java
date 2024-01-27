package chess.console.pieces;

import chess.console.Board;
import chess.console.Color;

import java.util.List;

public abstract class Piece {

    private final Color color;

    public Piece(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public abstract boolean isValidMove(Board board, String squareFrom, String squareTo);

    @Override
    public abstract String toString();

    /**
     * Used to optimize search for valid moves (instead of calling isValidMove with squareTo for every square on the board.
     */
    public abstract List<String> getValidMoves(Board board, String squareFrom); // TODO: potentially ensure that the moves do not put the king in check
}
