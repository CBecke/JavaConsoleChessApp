package chess.console.pieces;

import chess.console.Board;
import chess.console.Color;
import chess.console.MoveCalculator;
import chess.console.Square;

import java.util.Set;

public class Rook extends Piece {
    private boolean canCastle = true;

    public Rook(Color color) {
        super(color);
    }

    @Override
    public boolean isPseudoLegalPieceMove(Board board, Square squareFrom, Square squareTo) {
        return MoveCalculator.isRookMove(board, squareFrom, squareTo);
    }

    @Override
    public String toString() {
        return color == Color.WHITE ? "R" : "r";
    }

    @Override
    public Set<Square> getPseudoLegalPieceMoves(Board board, Square squareFrom) {
        int[][] moveDirections = new int[][] {{1,0}, {-1,0}, {0,1}, {0,-1}};
        return MoveCalculator.getValidMovesInDirections(board, squareFrom, moveDirections);
    }

    // TODO: make sure this is check in castling
    public boolean canCastle() { return canCastle; }

    public void disableCastling() { canCastle = false; }
}
