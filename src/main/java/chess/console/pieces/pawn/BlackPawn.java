package chess.console.pieces.pawn;

import chess.console.Board;

public class BlackPawn extends Pawn {
    public BlackPawn() {
        super(Color.BLACK);
    }

    @Override
    boolean isValidSingleForwardMove(String squareFrom, String squareTo) {
        int rankDiff = squareTo.charAt(1) - squareFrom.charAt(1);
        return rankDiff == -1;
    }

    @Override
    boolean isValidDoubleMove(String squareFrom, String squareTo) {
        int rankDiff = squareTo.charAt(1) - squareFrom.charAt(1);
        return isInInitialRank(squareFrom) && rankDiff == -2;
    }

    @Override
    boolean isInInitialRank(String square) {
        int rank = square.charAt(1) - '0';
        return rank == (Board.SIZE - 1);
    }
}
