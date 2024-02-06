package chess.console.pieces.pawn;

import chess.console.Board;
import chess.console.Color;
import chess.console.pieces.Piece;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public abstract class Pawn extends Piece {
    private final int direction;

    protected Pawn(Color color, int direction) {
        super(color);
        this.direction = direction;
    };
    @Override
    public boolean isValidMove(Board board, String squareFrom, String squareTo) {
        return (board.isEmpty(squareTo)
                    && (isValidDoubleMove(squareFrom, squareTo)
                        || isValidSingleForwardMove(squareFrom, squareTo)))
                || isValidCapture(board, squareFrom, squareTo);
    }

    @Override
    public Collection<String> getValidMoves(Board board, String squareFrom) {
        Collection<String> validMoves = new LinkedList<>();
        String toSquareCandidate = board.shiftSquare(squareFrom, 0, direction);

        // single square forward move
        if (board.isEmpty(toSquareCandidate)) {
            validMoves.add(toSquareCandidate);

            // double square forward move
            toSquareCandidate = board.shiftSquare(toSquareCandidate, 0, direction);
            if (board.isEmpty(toSquareCandidate)) { validMoves.add(toSquareCandidate); }
        }

        // captures
        for (int fileDirection : new int[]{-1, 1}) {
            toSquareCandidate = board.shiftSquare(squareFrom, fileDirection, direction);
            if (!board.isWithinBoard(toSquareCandidate)) { continue; }
            if (isValidCapture(board, squareFrom, toSquareCandidate)) { validMoves.add(toSquareCandidate); }
        }

        return validMoves;
    }

    abstract boolean isInInitialRank(String square);

    boolean isValidSingleForwardMove(String squareFrom, String squareTo) {
        int rankDiff = squareTo.charAt(1) - squareFrom.charAt(1);
        return rankDiff == direction;
    }

    boolean isValidDoubleMove(String squareFrom, String squareTo) {
        int rankDiff = squareTo.charAt(1) - squareFrom.charAt(1);
        return isInInitialRank(squareFrom) && rankDiff == 2*direction;
    }

    boolean isValidCaptureDirection(String squareFrom, String squareTo) {
        return squareTo.charAt(1) - squareFrom.charAt(1) == direction;
    }

    boolean isValidCapture(Board board, String squareFrom, String squareTo) {
        return !board.isEmpty(squareTo)
                && board.get(squareTo).getColor() != color
                && Math.abs(squareTo.charAt(0) - squareFrom.charAt(0)) == 1
                && isValidCaptureDirection(squareFrom, squareTo);
    };
}
