package chess.console.pieces.pawn;

import chess.console.Board;
import chess.console.Color;
import chess.console.pieces.Piece;

public abstract class Pawn extends Piece {
    private int direction;

    protected Pawn(Color color, int direction) {
        super(color);
        this.direction = direction;
    };
    @Override
    public boolean isValidMove(Board board, String squareFrom, String squareTo) {
        return (board.isEmpty(squareTo)
                    && (isValidDoubleMove(squareFrom, squareTo)
                        || isValidSingleForwardMove(squareFrom, squareTo)))
                || isValidCapture(board, squareFrom, squareTo);
    }

    abstract boolean isInInitialRank(String square);

    boolean isValidSingleForwardMove(String squareFrom, String squareTo) {
        int rankDiff = squareTo.charAt(1) - squareFrom.charAt(1);
        return rankDiff == direction;
    }

    boolean isValidDoubleMove(String squareFrom, String squareTo) {
        int rankDiff = squareTo.charAt(1) - squareFrom.charAt(1);
        return isInInitialRank(squareFrom) && rankDiff == 2*direction;
    }

    boolean isValidCaptureDirection(String squareFrom, String squareTo) {
        return squareTo.charAt(1) - squareFrom.charAt(1) == direction;
    }

    boolean isValidCapture(Board board, String squareFrom, String squareTo) {
        return !board.isEmpty(squareTo)
                && board.get(squareTo).getColor() != getColor()
                && Math.abs(squareTo.charAt(0) - squareFrom.charAt(0)) == 1
                && isValidCaptureDirection(squareFrom, squareTo);
    };
}
