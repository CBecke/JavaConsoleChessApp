package chess.console.pieces;

import chess.console.Board;
import chess.console.Color;

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
        // Get valid moves right and up
        List<String> validMoves = new LinkedList<>(getValidMovesInDirection(board, squareFrom, 1, 1));
        // Get valid moves left and up
        validMoves.addAll(getValidMovesInDirection(board, squareFrom, -1, 1));
        // check right and down
        validMoves.addAll(getValidMovesInDirection(board,squareFrom, 1, -1));
        // check left and down
        validMoves.addAll(getValidMovesInDirection(board, squareFrom, -1, -1));
        return validMoves;
    }

    private List<String> getValidMovesInDirection(Board board, String squareFrom, int fileDirection, int rankDirection) {
        List<String> validMoves = new LinkedList<>();
        String currentSquare = board.shiftSquare(squareFrom, fileDirection, rankDirection);

        while (board.isWithinBoard(currentSquare) && board.isEmpty(currentSquare)) {
            validMoves.add(currentSquare);
            currentSquare = board.shiftSquare(squareFrom, fileDirection, rankDirection);
        }
        return validMoves;
    }

    private boolean isDiagonalMove(int fileDiff, int rankDiff) { return fileDiff == rankDiff; }
}
