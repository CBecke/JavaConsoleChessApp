package chess.console.pieces;

import chess.console.Board;
import chess.console.Color;

public class Queen extends Piece {
    public Queen(Color color) {
        super(color);
    }

    @Override
    public boolean isValidMove(Board board, String squareFrom, String squareTo) {
        return isBishopMove(board,squareFrom, squareTo)
                || isRookMove(board, squareFrom, squareTo);
    }

    @Override
    public String toString() {
        return getColor() == Color.WHITE ? "Q" : "q";
    }

    private boolean isBishopMove(Board board, String squareFrom, String squareTo) {
        int fileDiff = Math.abs(squareTo.charAt(0) - squareFrom.charAt(0));
        int rankDiff = Math.abs(squareTo.charAt(1) - squareFrom.charAt(1));
        return isDiagonalMove(fileDiff, rankDiff)
                && board.isClearPath(squareFrom, squareTo);
    }
    private boolean isDiagonalMove(int fileDiff, int rankDiff) { return fileDiff == rankDiff; }

    private boolean isRookMove(Board board, String squareFrom, String squareTo) {
        return (isHorizontalMove(squareFrom, squareTo)
                || isVerticalMove(squareFrom, squareTo))
                && board.isClearPath(squareFrom, squareTo);
    }

    private boolean isVerticalMove(String squareFrom, String squareTo) {
        return (squareFrom.charAt(0) == squareTo.charAt(0)) && (squareFrom.charAt(1) != squareTo.charAt(1));
    }

    private boolean isHorizontalMove(String squareFrom, String squareTo) {
        return (squareFrom.charAt(0) != squareTo.charAt(0)) && (squareFrom.charAt(1) == squareTo.charAt(1));
    }
}
