package chess.console.pieces.pawn;

import chess.console.Board;
import chess.console.Color;

public class WhitePawn extends Pawn {
    public WhitePawn() {
        super(Color.WHITE, 1);
    }

    @Override
    boolean isInInitialRank(String square) {
        int rank = square.charAt(1) - '0';
        return rank == 2;
    }

    @Override
    public String toString() {
        return "P";
    }
}
