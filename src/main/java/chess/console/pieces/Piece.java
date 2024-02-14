package chess.console.pieces;

import chess.console.Board;
import chess.console.Color;
import chess.console.Square;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public abstract class Piece {

    protected final Color color;

    protected Piece(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    // TODO: make sure king is not in check when a move is made (unless it stops check)
    // TODO: make sure the current move does not put the king in check

    /**
     * Determines if a piece can move from squareFrom to squareTo on board. It tests general conditions are met and
     * calls the abstract method isValidPieceMove in which the individual piece's logic rules are tested.
     */
    public boolean isLegalMove(Board board, Square squareFrom, Square squareTo) {
        return (board.isEmpty(squareTo)
                    || isOppositeColor(board.get(squareTo)))
                && !squareFrom.equals(squareTo) // still-standing move
                && isLegalPieceMove(board, squareFrom, squareTo)
                && !board.putsOwnKingInCheck(this, squareFrom); // checked after isValidPieceMove to avoid cycle
    }

    /**
     * tests piece type specific conditions (such as diagonal move for bishop).
     */
    protected abstract boolean isLegalPieceMove(Board board, Square squareFrom, Square squareTo);

    private boolean isOppositeColor(Piece other) { return color != other.getColor(); }

    @Override
    public abstract String toString();

    protected abstract Collection<Square> getLegalPieceMoves(Board board, Square squareFrom);

    /**
     * Retrieves the set of valid moves.Used to optimize search for valid moves (instead of calling isValidMove with squareTo for every square on the
     * board).
     */
    public Collection<Square> getLegalMoves(Board board, Square squareFrom) {
        if (board.putsOwnKingInCheck(board.get(squareFrom), squareFrom)) { return new HashSet<>(); }
        return getLegalPieceMoves(board, squareFrom);
    }

    public boolean canMove(Board board, Square squareFrom) {
        return !getLegalMoves(board, squareFrom).isEmpty();
    }

    public static Set<Square> getPseudoLegalMoves(Piece piece, Board board, Square squareFrom) {
        return piece.getPseudoLegalPieceMoves(board, squareFrom);
    }

    public abstract Set<Square> getPseudoLegalPieceMoves(Board board, Square squareFrom);

    ;


}
