package chess.console.pieces.pawn;

import chess.console.Board;
import chess.console.Color;
import chess.console.Square;

public class BlackPawn extends Pawn {
    public BlackPawn() { super(Color.BLACK, -1); }

    @Override
    boolean isInInitialRank(Square square) { return square.getRank() == (Board.size - 1); }

    @Override
    public String toString() {
        return "p";
    }
}
