package chess.console.pieces;

import chess.console.Board;
import chess.console.Color;
import chess.console.Square;
import chess.console.pieces.pawn.BlackPawn;
import chess.console.pieces.pawn.Pawn;
import chess.console.pieces.pawn.WhitePawn;

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
     * Tests if the move from squareFrom to squareTo on board is pseudo-legal. A move is pseudo-legal if it can be
     * performed without considering whether the king is already or will be put into check when the move is made. That
     * is, legal moves are a subset og pseudo-legal moves and the set difference between them is the moves that are
     * illegal because the player has to prevent their king from being in check.
     */
    public boolean isPseudoLegalMove(Board board, Square squareFrom, Square squareTo) {
        return (board.isEmpty(squareTo)
                    || isOppositeColor(board.get(squareTo)))
                && !squareFrom.equals(squareTo) // still-standing move
                && isPseudoLegalPieceMove(board, squareFrom, squareTo);
    }

    /**
     * tests piece type specific conditions (such as diagonal move for bishop).
     */
    protected abstract boolean isPseudoLegalPieceMove(Board board, Square squareFrom, Square squareTo);

    private boolean isOppositeColor(Piece other) { return color != other.getColor(); }

    @Override
    public abstract String toString();

    /**
     * a move is pseudo-legal if it can be performed without considering whether the king is already or will be put
     * into check when the move is made. That is, legal moves are a subset og pseudo-legal moves and the set difference
     * between them is the moves that are illegal because the player has to prevent their king from being in check.
     */
    public abstract Set<Square> getPseudoLegalPieceMoves(Board board, Square squareFrom);

    public boolean canMove(Board board, Square squareFrom) {
        for (Square squareTo : getPseudoLegalPieceMoves(board, squareFrom)) {
            if (!board.putsOwnKingInCheck(squareFrom, squareTo))
                { return true; }
        }
        return false;
    }
}
