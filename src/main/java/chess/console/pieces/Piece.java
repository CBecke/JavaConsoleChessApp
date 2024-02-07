package chess.console.pieces;

import chess.console.Board;
import chess.console.Color;
import chess.console.Square;

import java.util.Collection;
import java.util.HashSet;

public abstract class Piece {

    protected final Color color;

    protected Piece(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }


    /**
     * Determines if a piece can move from squareFrom to squareTo on board. It tests general conditions are met and
     * calls the abstract method isValidPieceMove in which the individual piece's logic rules are tested.
     */
    public boolean isValidMove(Board board, Square squareFrom, Square squareTo) {
        return (board.isEmpty(squareTo)
                    || isOppositeColor(board.get(squareTo)))
                && !squareFrom.equals(squareTo) // still-standing move
                && !board.putsOwnKingInCheck(this, squareFrom)
                && isValidPieceMove(board, squareFrom, squareTo);

    }

    private boolean isOppositeColor(Piece other) { return color != other.getColor(); }

    /**
     * tests piece type specific conditions (such as diagonal move for bishop).
     */
    protected abstract boolean isValidPieceMove(Board board, Square squareFrom, Square squareTo);

    @Override
    public abstract String toString();

    protected abstract Collection<Square> getValidPieceMoves(Board board, Square squareFrom);

    /**
     * Retrieves the set of valid moves.Used to optimize search for valid moves (instead of calling isValidMove with squareTo for every square on the
     * board).
     */
    public Collection<Square> getValidMoves(Board board, Square squareFrom) {
        if (board.putsOwnKingInCheck(board.get(squareFrom), squareFrom)) { return new HashSet<>(); }
        return getValidPieceMoves(board, squareFrom);
    }

    public boolean canMove(Board board, Square squareFrom) {
        return !getValidMoves(board, squareFrom).isEmpty();
    }

}
