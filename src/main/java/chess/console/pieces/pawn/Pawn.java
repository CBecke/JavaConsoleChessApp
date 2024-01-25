package chess.console.pieces.pawn;

import chess.console.Board;
import chess.console.pieces.Piece;

public abstract class Pawn extends Piece {
    public Pawn(Color color) {
        super(color);
    }

    @Override
    public boolean isValidMove(Board board, String squareFrom, String squareTo) {
        return isValidDoubleMove(squareFrom, squareTo)
                || isValidSingleForwardMove(squareFrom, squareTo);
    }

    abstract boolean isValidSingleForwardMove(String squareFrom, String squareTo);

    abstract boolean isValidDoubleMove(String squareFrom, String squareTo);


    abstract boolean isInInitialRank(String square);
}
