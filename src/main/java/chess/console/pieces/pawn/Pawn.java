package chess.console.pieces.pawn;

import chess.console.Board;
import chess.console.Color;
import chess.console.Square;
import chess.console.pieces.Piece;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public abstract class Pawn extends Piece {
    private final int direction;

    protected Pawn(Color color, int direction) {
        super(color);
        this.direction = direction;
    }

    @Override
    public boolean isLegalPieceMove(Board board, Square squareFrom, Square squareTo) {
        return (board.isEmpty(squareTo)
                    && (isValidDoubleMove(squareFrom, squareTo)
                        || isValidSingleForwardMove(squareFrom, squareTo)))
                || isValidCapture(board, squareFrom, squareTo);
    }

    @Override
    public Set<Square> getPseudoLegalPieceMoves(Board board, Square squareFrom) {
        Set<Square> validMoves = new HashSet<>();
        Square toSquareCandidate = squareFrom.shift(0, direction);

        // single square forward move
        if (board.isEmpty(toSquareCandidate)) {
            validMoves.add(toSquareCandidate);

            // double square forward move
            toSquareCandidate = toSquareCandidate.shift(0, direction);
            if (board.isEmpty(toSquareCandidate)) { validMoves.add(toSquareCandidate); }
        }

        // captures
        for (int fileDirection : new int[]{-1, 1}) {
            toSquareCandidate = squareFrom.shift(fileDirection, direction);
            if (board.isWithinBoard(toSquareCandidate) && isValidCapture(board, squareFrom, toSquareCandidate))
                { validMoves.add(toSquareCandidate); }
        }

        return validMoves;
    }

    abstract boolean isInInitialRank(Square square);

    boolean isValidSingleForwardMove(Square squareFrom, Square squareTo) {
        int rankDiff = Square.absRankDiff(squareFrom, squareTo);
        return rankDiff == direction;
    }

    boolean isValidDoubleMove(Square squareFrom, Square squareTo) {
        // intentionally not taking absolute rank difference due to direction
        int rankDiff = squareTo.getRank() - squareFrom.getRank();
        return isInInitialRank(squareFrom) && rankDiff == 2*direction;
    }

    boolean isValidCaptureDirection(Square squareFrom, Square squareTo) {
        return squareTo.getRank() - squareFrom.getRank() == direction;
    }

    boolean isValidCapture(Board board, Square squareFrom, Square squareTo) {
        return !board.isEmpty(squareTo)
                && board.get(squareTo).getColor() != color
                && Square.absFileDiff(squareFrom, squareTo) == 1
                && isValidCaptureDirection(squareFrom, squareTo);
    }

}
