package chess.console;

import chess.console.pieces.King;
import chess.console.pieces.Piece;
import chess.console.pieces.pawn.Pawn;

import java.util.Collection;
import java.util.LinkedList;

public class MoveLogger {
    Collection<String> log = new LinkedList<>();

    /**
     * Records the move AFTER it has been played (and was determined to be valid). That is, on board the piece has
     * already moved from squareFrom to squareTo.
     */
    public void log(Board board, String squareFrom, String squareTo) {
        // the piece has already moved to squareTo when this is called
        Piece mover = board.get(squareTo);

        // check castles
        if (mover instanceof King && Math.abs(squareTo.charAt(0) - squareFrom.charAt(0)) == 2)
            { log.add(((squareTo.charAt(0) == 'c') ? "O-O-O" : "O-O" )); }

        String move;
        // check if pawn or different piece
        if (mover instanceof Pawn) {
            move = String.valueOf(squareFrom.charAt(0));
            // check promotion
        }
        // check capture

        // check for check/checkmate
    }
}
