package chess.console.pieces.pawn;

import chess.console.Board;
import chess.console.Color;

public class BlackPawn extends Pawn {
    public BlackPawn() { super(Color.BLACK, -1); }

    @Override
    boolean isInInitialRank(String square) {
        int rank = square.charAt(1) - '0';
        return rank == (Board.SIZE - 1);
    }

    @Override
    public String toString() {
        return "p";
    }
}
