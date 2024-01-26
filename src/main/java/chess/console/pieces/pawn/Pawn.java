package chess.console.pieces.pawn;

import chess.console.Board;
import chess.console.Color;
import chess.console.pieces.Piece;

public abstract class Pawn extends Piece {
    public Pawn(Color color) {
        super(color);
    }

    @Override
    public boolean isValidMove(Board board, String squareFrom, String squareTo) {
        return (board.isEmpty(squareTo)
                    && (isValidDoubleMove(squareFrom, squareTo)
                        || isValidSingleForwardMove(squareFrom, squareTo)))
                || isValidCapture(board, squareFrom, squareTo);
    }

    abstract boolean isValidSingleForwardMove(String squareFrom, String squareTo);

    abstract boolean isValidDoubleMove(String squareFrom, String squareTo);

    abstract boolean isInInitialRank(String square);

    abstract boolean isValidCaptureDirection(String squareFrom, String squareTo);

    boolean isValidCapture(Board board, String squareFrom, String squareTo) {
        return !board.isEmpty(squareTo)
                && board.get(squareTo).getColor() != getColor()
                && Math.abs(squareTo.charAt(0) - squareFrom.charAt(0)) == 1
                && isValidCaptureDirection(squareFrom, squareTo);
    };
}
