package chess.console.pieces;

import chess.console.Board;

public class Pawn extends Piece {
    public Pawn(Color color) {
        super(color);
    }

    @Override
    public boolean isValidMove(Board board, String squareFrom, String squareTo) {
        return isValidDoubleMove(squareFrom, squareTo)
                || isValidSingleForwardMove(squareFrom, squareTo);
    }

    private boolean isValidSingleForwardMove(String squareFrom, String squareTo) {
        int rankDiff = squareTo.charAt(1) - squareFrom.charAt(1);
        Color color = getColor();
        return (color == Color.BLACK && rankDiff == -1)
                || (color == Color.WHITE && rankDiff == 1);
    }

    private boolean isValidDoubleMove(String squareFrom, String squareTo) {
        int rankDiff = squareTo.charAt(1) - squareFrom.charAt(1);
        Color color = getColor();
        return isInInitialRank(squareFrom)
                && ((color == Color.BLACK && rankDiff == -2)
                    || (color == Color.WHITE && rankDiff == 2));
    }

    private boolean isInInitialRank(String square) {
        int rank = square.charAt(1) - '0';
        return (getColor() == Color.BLACK && rank == (Board.SIZE - 1))
                || (getColor() == Color.WHITE && rank == 2);
    }
}
