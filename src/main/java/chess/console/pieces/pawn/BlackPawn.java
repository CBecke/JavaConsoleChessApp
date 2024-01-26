package chess.console.pieces.pawn;

import chess.console.Board;
import chess.console.Color;

public class BlackPawn extends Pawn {
    private final int direction = -1;
    public BlackPawn() {
        super(Color.BLACK);
    }

    @Override
    boolean isValidSingleForwardMove(String squareFrom, String squareTo) {
        int rankDiff = squareTo.charAt(1) - squareFrom.charAt(1);
        return rankDiff == direction;
    }

    @Override
    boolean isValidDoubleMove(String squareFrom, String squareTo) {
        int rankDiff = squareTo.charAt(1) - squareFrom.charAt(1);
        return isInInitialRank(squareFrom) && rankDiff == 2*direction;
    }

    @Override
    boolean isInInitialRank(String square) {
        int rank = square.charAt(1) - '0';
        return rank == (Board.SIZE - 1);
    }

    @Override
    boolean isValidCaptureDirection(String squareFrom, String squareTo) {
        return squareTo.charAt(1) - squareFrom.charAt(1) == direction;
    }

    @Override
    public String toString() {
        return "p";
    }
}
