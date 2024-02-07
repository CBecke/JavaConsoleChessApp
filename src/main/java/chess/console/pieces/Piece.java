package chess.console.pieces;

import chess.console.Board;
import chess.console.Color;
import chess.console.Square;

import java.util.Collection;
import java.util.List;

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
                && isValidPieceMove(board, squareFrom, squareTo)
                && !board.putsOwnKingInCheck(this, squareFrom);

    }

    private boolean isOppositeColor(Piece other) { return color != other.getColor(); }

    /**
     * tests piece type specific conditions (such as diagonal move for bishop).
     */
    protected abstract boolean isValidPieceMove(Board board, Square squareFrom, Square squareTo);

    @Override
    public abstract String toString();

    /**
     * Used to optimize search for valid moves (instead of calling isValidMove with squareTo for every square on the
     * board. Any sub-class implementation must
     */
    public abstract Collection<Square> getValidMoves(Board board, Square squareFrom); // TODO: potentially ensure that the moves do not put the king in check

    public boolean canMove(Board board, Square squareFrom) {
        return !getValidMoves(board, squareFrom).isEmpty();
    }

}
