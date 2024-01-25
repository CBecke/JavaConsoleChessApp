package chess.console.pieces;

import chess.console.Board;

public class Rook extends Piece {
    private boolean canCastle = true;

    public Rook(Piece.Color color) {
        super(color);
    }

    @Override
    public boolean isValidMove(Board board, String squareFrom, String squareTo) {
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

    public boolean canCastle() { return canCastle; }

    public void disableCastling() { canCastle = false; }
}
