package chess.console.pieces;

import chess.console.Board;
import chess.console.Color;
import chess.console.MoveCalculator;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

public class Bishop extends Piece {
    public Bishop(Color color) { super(color); }

    @Override
    public boolean isValidMove(Board board, String squareFrom, String squareTo) {
        int fileDiff = Math.abs(squareTo.charAt(0) - squareFrom.charAt(0));
        int rankDiff = Math.abs(squareTo.charAt(1) - squareFrom.charAt(1));
        return isDiagonalMove(fileDiff, rankDiff)
                && board.isClearPath(squareFrom, squareTo);
    }

    @Override
    public String toString() {
        return getColor() == Color.WHITE ? "B" : "b";
    }

    @Override
    public List<String> getValidMoves(Board board, String squareFrom) {
        // moveDirection contains doubles where the first element is the file direction and the second is the rank direction
        int[][] moveDirections = new int[][] {{1,1}, {1,-1}, {-1,1}, {-1,-1}};
        return MoveCalculator.getValidMovesInDirections(board, squareFrom, moveDirections);
    }

    private boolean isDiagonalMove(int fileDiff, int rankDiff) { return fileDiff == rankDiff; }
}
