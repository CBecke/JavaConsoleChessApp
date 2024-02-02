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
        if (mover instanceof King && Math.abs(squareTo.charAt(0) - squareFrom.charAt(0)) == 2) {
            log.add(((squareTo.charAt(0) == 'c') ? "O-O-O" : "O-O" ));
            return;
        }

        // check pawn promotion
        if (mover instanceof Pawn && board.isEdgeRank(squareTo)) {
            log.add(squareTo + "=" + board.get(squareTo).toString()); // e.g. "e8=Q"
            return;
        }

        String move = (mover instanceof Pawn) ? String.valueOf(squareFrom.charAt(0)) : mover.toString();
        if (board.wasCapture()) { move = move + "x"; }
        move = move + squareTo;

        // check for check/checkmate
        Color oppositeColor = (mover.getColor() == Color.WHITE) ? Color.WHITE : Color.BLACK;
        String oppositeKingSquare = board.getKingSquare(oppositeColor);

        if(board.isCheckmate(oppositeKingSquare)) { log.add(move + "#"); }
        else if (mover.isValidMove(board, squareTo, oppositeKingSquare)) { log.add(move + "+"); } // king put in check
        else { log.add(move); }
    }
}
