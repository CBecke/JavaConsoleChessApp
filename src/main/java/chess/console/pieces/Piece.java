package chess.console.pieces;

import chess.console.Board;

public abstract class Piece {
    public static enum Color {
        WHITE,
        BLACK
    }

    private final Color color;

    public Piece(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public abstract boolean isValidMove(Board board, String squareFrom, String squareTo);

}
