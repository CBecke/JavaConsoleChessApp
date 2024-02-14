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

    protected abstract Set<Square> getLegalPieceMoves(Board board, Square squareFrom);

    /**
     * Retrieves the set of valid moves.Used to optimize search for valid moves (instead of calling isValidMove with squareTo for every square on the
     * board).
     */
    public Set<Square> getLegalMoves(Board board, Square squareFrom) {
        if (board.putsOwnKingInCheck(board.get(squareFrom), squareFrom)) { return new HashSet<>(); }
        return getLegalPieceMoves(board, squareFrom);
    }

    public boolean canMove(Board board, Square squareFrom) {
        return !getLegalMoves(board, squareFrom).isEmpty();
    }

    /**
     * a move is pseudo-legal if it can be performed without considering whether the king is already or will be put
     * into check when the move is made. That is, legal moves are a subset og pseudo-legal moves and the set difference
     * between them is the moves that are illegal because the player has to prevent their king from being in check.
     */
    public static Set<Square> getPseudoLegalMoves(Piece piece, Board board, Square squareFrom) {
        return piece.getPseudoLegalPieceMoves(board, squareFrom);
    }

    public abstract Set<Square> getPseudoLegalPieceMoves(Board board, Square squareFrom);

    public static Set<Square> getAllPseudoLegalPieceMoves(Board board, Square squareFrom, Color color) {
        Set<Square> pseudoMoves = new HashSet<>();
        (new Knight(Color.WHITE)).getPseudoLegalPieceMoves(board, squareFrom);
    }

}
