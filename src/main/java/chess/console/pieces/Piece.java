package chess.console.pieces;

import chess.console.Board;
import chess.console.Color;

public abstract class Piece {

    private final Color color;

    public Piece(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public abstract boolean isValidMove(Board board, String squareFrom, String squareTo);

}
