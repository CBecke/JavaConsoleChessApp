package chess.console.pieces.pawn;

import chess.console.Board;
import chess.console.Color;
import chess.console.Square;

public class WhitePawn extends Pawn {
    public WhitePawn() {
        super(Color.WHITE, 1);
    }

    @Override
    boolean isInInitialRank(Square square) { return square.getRank() == 2; }

    @Override
    public String toString() {
        return "P";
    }
}
