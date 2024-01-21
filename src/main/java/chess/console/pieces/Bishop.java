package chess.console.pieces;

import chess.console.Board;

public class Bishop extends Piece {
    public Bishop(Color color) { super(color); }

    @Override
    public boolean isValidMove(Board board, String squareFrom, String squareTo) {
        int fileDiff = Math.abs(squareTo.charAt(0) - squareFrom.charAt(0));
        int rankDiff = Math.abs(squareTo.charAt(1) - squareFrom.charAt(1));
        return isDiagonalMove(fileDiff, rankDiff)
                && board.isClearPath(squareFrom, squareTo);
    }

    private boolean isDiagonalMove(int fileDiff, int rankDiff) { return fileDiff == rankDiff; }
}
