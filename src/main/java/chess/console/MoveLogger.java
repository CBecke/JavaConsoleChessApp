package chess.console;

import chess.console.ZobristHashing.ZobristTable;
import chess.console.pieces.King;
import chess.console.pieces.Piece;
import chess.console.pieces.pawn.Pawn;

import java.util.*;

public class MoveLogger {
    private final LinkedList<String> log = new LinkedList<>();
    private final ZobristTable zobristTable = new ZobristTable();
    private final Map<Long, Integer> positionCount = new HashMap<>();
    private boolean threeFoldRepetition = false;

    /**
     * Records the move AFTER it has been played (and was determined to be valid). That is, on board the piece has
     * already moved from squareFrom to squareTo.
     */
    public void log(Board board, Square squareFrom, Square squareTo) {
        log.add(getMoveString(board, squareFrom, squareTo));
        zobristTable.updateHash(board, squareFrom, squareTo);
        long positionHash = zobristTable.getHash();
        positionCount.merge(positionHash, 1, Integer::sum);
        if (positionCount.get(positionHash) >= 3) { threeFoldRepetition = true; }
    }

    // TODO: log ambiguous move correctly (e.g. if both knights/rooks can move to the same square)
    // TODO: log promotion correctly
    private String getMoveString(Board board, Square squareFrom, Square squareTo) {
        // the piece has already moved to squareTo when this is called
        Piece mover = board.get(squareTo);

        // check castles
        if (mover instanceof King && Square.absFileDiff(squareFrom, squareTo) == 2)
            { return (squareTo.getCharFile() == 'c') ? "O-O-O" : "O-O"; }

        // check pawn promotion
        if (mover instanceof Pawn && board.isEdgeRank(squareTo))
            { return squareTo + "=" + board.get(squareTo).toString(); } // e.g. "e8=Q"

        String move = (mover instanceof Pawn) ? "" : mover.toString();
        if (board.wasCapture()) {
            move = move + ((mover instanceof Pawn) ? squareFrom.getCharFile() : "") + "x";
        }
        move = move + squareTo;

        // check for check/checkmate
        Color oppositeColor = (mover.getColor() == Color.WHITE) ? Color.WHITE : Color.BLACK;
        Square oppositeKingSquare = board.getKingSquare(oppositeColor);

        if(board.isCheckmate(oppositeKingSquare)) { return move + "#"; }
        else if (board.isLegalMove(squareTo, oppositeKingSquare)) { return move + "+"; } // king put in check
        return move;
    }

    public boolean isFiftyMoveDraw() {
        if (log.size() < 50) { return false; }
        Iterator<String> iterator = log.descendingIterator();
        int i = 1;
        while (i <= 50) {
            String move = iterator.next();
            if (isPawnMove(move)) { return false; }
            i+= 1;
        }
        return true;
    }

    private boolean isPawnMove(String move) {
        char mover = move.charAt(0);
        return 'a' <= mover && mover <= 'h';
    }

    public boolean isThreeFoldRepetition() {
        return threeFoldRepetition;
    }
}
