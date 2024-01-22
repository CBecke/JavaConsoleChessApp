package stepDefinitions;

import chess.console.Board;
import chess.console.pieces.Piece;

public class King extends Piece{
    public King(Color color) {
        super(color);
    }

    @Override
    public boolean isValidMove(Board board, String squareFrom, String squareTo) {
        int fileDiff = Math.abs(squareTo.charAt(0) - squareFrom.charAt(0));
        int rankDiff = Math.abs(squareTo.charAt(1) - squareFrom.charAt(1));
        return fileDiff <= 1 && rankDiff <= 1 && !board.isAttacked(this, squareTo);
    }
}
