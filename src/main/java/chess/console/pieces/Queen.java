package chess.console.pieces;

import chess.console.Board;
import chess.console.Color;
import chess.console.MoveCalculator;

import java.util.Collection;
import java.util.List;

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
    public String toString() { return color == Color.WHITE ? "Q" : "q"; }

    @Override
    public Collection<String> getValidMoves(Board board, String squareFrom) {
        int[][] moveDirections = new int[][] {{1,1}, {1,-1}, {-1,1}, {-1,-1}, // diagonal
                                              {1,0}, {-1,0}, {0,1}, {0,-1}};  // horizontal and vertical
        return MoveCalculator.getValidMovesInDirections(board, squareFrom, moveDirections);
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
